    package com.github.wellfernandes.shoppinglistmanager.controller;

    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.graphics.Color;
    import android.os.Bundle;
    import android.view.ContextMenu;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ListView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.app.AppCompatDelegate;
    import androidx.appcompat.view.ActionMode;
    import androidx.lifecycle.LiveData;
    import androidx.lifecycle.Observer;
    import androidx.lifecycle.ViewModelProvider;

    import com.github.wellfernandes.shoppinglistmanager.R;
    import com.github.wellfernandes.shoppinglistmanager.constants.AppConstants;
    import com.github.wellfernandes.shoppinglistmanager.constants.ErrorConstants;
    import com.github.wellfernandes.shoppinglistmanager.controller.viewmodel.ShoppingListViewModel;
    import com.github.wellfernandes.shoppinglistmanager.database.DatabaseConnection;
    import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;
    import com.github.wellfernandes.shoppinglistmanager.utils.Alerts;

    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

    public class ShoppingListActivity extends AppCompatActivity {
        private ListView listViewDefault;
        private ShoppingListViewModel shoppingListViewModel;
        private ShoppingListAdapter shoppingListAdapter;
        private ActionMode actionMode;
        private View selectedView;

        private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.main_view_context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (AppConstants.SELECTED_POSITION == -1) {
                    return ShoppingListActivity.super.onContextItemSelected(item);
                }

                if (item.getItemId() == R.id.menuItemEdit) {
                    editList(AppConstants.SELECTED_POSITION);
                    mode.finish();
                    return true;
                } else if (item.getItemId() == R.id.menuItemDelete) {
                    ShoppingList selectedList = shoppingListViewModel.
                            getAllShoppingLists().getValue().get(AppConstants.SELECTED_POSITION);
                    deleteList(selectedList.getId());
                    mode.finish();
                    return true;
                }

                AppConstants.SELECTED_POSITION = -1;
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                if(selectedView != null) {
                    selectedView.setBackgroundColor(Color.TRANSPARENT);
                }

                actionMode = null;
                selectedView = null;

                listViewDefault.setEnabled(true);
            }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.default_shopping_lists);

            listViewDefault = findViewById(R.id.listViewDefaultShoppingLists);

            setTitle(getString(R.string.view_name_shopping_lists));

            populateListViewShoppingLists();
            listViewDefault.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ShoppingList clickedList = shoppingListViewModel.getAllShoppingLists().getValue().get(position);
                    String itemName = clickedList.getName();

                    AppConstants.SELECTED_POSITION = position;
                    view.setSelected(true);

                    Toast.makeText(ShoppingListActivity.this, getString(R.string.itemClicked) + itemName, Toast.LENGTH_SHORT).show();
                }
            });

            registerForContextMenu(listViewDefault);

            listViewDefault.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            listViewDefault.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    if(actionMode != null) {
                        return false;
                    }

                    AppConstants.SELECTED_POSITION = position;
                    selectedView = view;
                    selectedView.setBackgroundColor(Color.LTGRAY);
                    listViewDefault.setEnabled(false);

                    actionMode = startSupportActionMode(actionModeCallback);

                    return true;
                }
            });

            readSharedPreference();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_view_options_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() == R.id.menuItemAdd) {
                Intent intent = new Intent(this, ListRegistrationActivity.class);
                startActivityForResult(intent, AppConstants.REQUEST_CODE);
                return true;
            }else if(item.getItemId() == R.id.menuItemAbout) {
                Intent intent = new Intent(this, AboutAppActivity.class);
                startActivity(intent);
                return true;
            }else if(item.getItemId() == R.id.menuItemLightTheme) {
                saveSharedPreference(AppCompatDelegate.MODE_NIGHT_NO);
                return true;
            }else if(item.getItemId() == R.id.menuItemDarkTheme) {
                saveSharedPreference(AppCompatDelegate.MODE_NIGHT_YES);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
            switch (AppConstants.DEFAULT_THEME_OPTION) {
                case AppCompatDelegate.MODE_NIGHT_NO:
                    menu.findItem(R.id.menuItemLightTheme).setChecked(true);
                    return true;
                case AppCompatDelegate.MODE_NIGHT_YES:
                    menu.findItem(R.id.menuItemDarkTheme).setChecked(true);
                    return true;
                default:
                    return super.onPrepareOptionsMenu(menu);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            getMenuInflater().inflate(R.menu.main_view_context_menu, menu);
        }

        @Override
        public boolean onContextItemSelected(@NonNull MenuItem item) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            if(item.getItemId() == R.id.menuItemEdit) {
                editList(menuInfo.position);
                Toast.makeText(this, R.string.toast_message_edit, Toast.LENGTH_SHORT).show();
            }else if(item.getItemId() == R.id.menuItemDelete) {
                deleteList(menuInfo.position);
                Toast.makeText(this, R.string.toast_message_deleted, Toast.LENGTH_SHORT).show();
            }
            return super.onContextItemSelected(item);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == AppConstants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                String listName = data.getStringExtra(AppConstants.EXTRA_NEW_LIST_NAME);
                String listPriority = data.getStringExtra(AppConstants.EXTRA_LIST_PRIORITY);

                ShoppingList newShoppingList = new ShoppingList(listName, new Date(), listPriority);

                DatabaseConnection databaseConnection = DatabaseConnection.getInstance(this);
                databaseConnection.shoppingListRepository().insert(newShoppingList);
            }

            shoppingListAdapter.notifyDataSetChanged();
        }

        private void populateListViewShoppingLists() {
            shoppingListViewModel = new ViewModelProvider(
                    this).get(ShoppingListViewModel.class);

            shoppingListAdapter = new ShoppingListAdapter(
                    ShoppingListActivity.this, new ArrayList<>());

            listViewDefault.setAdapter(shoppingListAdapter);
            shoppingListViewModel.getAllShoppingLists().observe(this, new Observer<List<ShoppingList>>() {
                @Override
                public void onChanged(List<ShoppingList> shoppingLists) {
                    shoppingListAdapter.setInfoShoppingList(shoppingLists);
                    shoppingListAdapter.notifyDataSetChanged();
                }
            });
        }

        private void editList(int position) {
            if (position >= 0 && position < shoppingListAdapter.getCount()) {
                ShoppingList selectedList = (ShoppingList) shoppingListAdapter.getItem(position);

                Intent intent = new Intent(this, ListRegistrationActivity.class);
                intent.putExtra(AppConstants.EXTRA_LIST_ID, selectedList.getId());
                intent.putExtra(AppConstants.EXTRA_LIST_NAME, selectedList.getName());
                intent.putExtra(AppConstants.EXTRA_LIST_PRIORITY, selectedList.getPriority());
                intent.putExtra(AppConstants.EXTRA_EDITED_POSITION, position);
                startActivityForResult(intent, AppConstants.REQUEST_CODE);
            }
        }

        private void deleteList(int listPosition) {
            LiveData<ShoppingList> shoppingListById = shoppingListViewModel.getShoppingListById(listPosition);
            shoppingListById.observe(this, new Observer<ShoppingList>() {
                @Override
                public void onChanged(ShoppingList shoppingList) {
                    if (shoppingList != null) {
                        DialogInterface.OnClickListener listener =
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                DatabaseConnection databaseConnection = DatabaseConnection.getInstance(ShoppingListActivity.this);
                                                databaseConnection.shoppingListRepository().delete(shoppingList);
                                                //shoppingListViewModel.delete(shoppingList);
                                                databaseConnection.shoppingListRepository().delete(shoppingList);
                                                shoppingListAdapter.notifyDataSetChanged();
                                                break;
                                            case DialogInterface.BUTTON_NEGATIVE:
                                                break;
                                        }
                                    }
                                };

                        Alerts.alertConfirm(ShoppingListActivity.this, getString(R.string.message_confirm_delete), listener);
                    } else {
                        Toast.makeText(ShoppingListActivity.this, ErrorConstants.ERROR_DELETE_LIST, Toast.LENGTH_SHORT).show();
                    }

                    shoppingListById.removeObserver(this);
                }
            });
        }

        public void readSharedPreference() {
            SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            AppConstants.DEFAULT_THEME_OPTION = sharedPreferences.getInt(AppConstants.APP_THEME, AppConstants.DEFAULT_THEME_OPTION);

            setThemeOption();
        }

        public void saveSharedPreference(int themeOption) {
            SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt(AppConstants.APP_THEME, themeOption);
            editor.apply();

            AppConstants.DEFAULT_THEME_OPTION = themeOption;
            setThemeOption();
        }

        public void setThemeOption() {
            AppCompatDelegate.setDefaultNightMode(AppConstants.DEFAULT_THEME_OPTION);
        }
    }