# AndroidNavigationDrawerWithFragments
Example of an Android Application where when a Navigation Drawer item is clicked in the MainActivity, the old fragment is replaced by a new one with a fade in and fade out animation after the drawer is closed.<br/>
<p align="center">
  <img src="https://user-images.githubusercontent.com/32685864/41197950-6ebcd328-6c64-11e8-8b94-e4cbf48cf9a6.jpg" width="280">
  <img src="https://user-images.githubusercontent.com/32685864/41197988-49deceb6-6c65-11e8-9144-4819c52c740b.jpg" width="280">
  <img src="https://user-images.githubusercontent.com/32685864/41197992-674126d4-6c65-11e8-8c36-87a5ef0a49cb.jpg" width="280">
</p>
### How it works

There are two fragments in this example: FragmentOne.java, FragmentTwo.java.<br/>
The FragmentOne is loaded when the application is first lunched, then whenever another option is clicked (e.g. Gallery), the FragmentOne, or any other fragment, is replaced with the fragment that is assigned in the onNavigationItemSelected method

```java
public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();
    drawerMenuItemSelected = item;
    fragment = null;
    Class fragmentClass = null;

    //TODO: Change the navigation view items to match the application's fragments
    switch (id) {
        case R.id.nav_camera:
            fragmentClass = FragmentOne.class;
            break;
        case R.id.nav_gallery:
            fragmentClass = FragmentTwo.class;
            break;
        case R.id.nav_slideshow:

            break;
        case R.id.nav_manage:

            break;
        case R.id.nav_share:

            break;
        case R.id.nav_send:

            break;
    }

    try {
        fragment = (Fragment) fragmentClass.newInstance();
    } catch (Exception e) {
        e.printStackTrace();
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    //Replace the fragment selected after the drawer is closed
    drawer.addDrawerListener(this);
    drawer.closeDrawer(GravityCompat.START);
    return true;
}
```
* The fragment variable is a Fragment Object
* fragmentClass is a Class Object to which a fragment class is assigned depending on the type of the fragment (in this case FragmentOne, FragmentTwo)
* Then a new instance is created of that class and assigned to the generic fragment object (casted)
* In the end, a DrawerLayout.DrawerListener is added, so it is possible to replaced the fragment without lag after the drawer is finished closing

```java

    private void replaceFragment() {
        //Replace fragment in the fragmentContent
        if (fragment != null) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //TODO: Replace the enter and exit animations if needed
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.fragmentContent, fragment, TAG)
                    .addToBackStack(null)
                    .commit();

            // Highlight the selected item has been done by NavigationView
            drawerMenuItemSelected.setChecked(true);
            // Set action bar title
            setTitle(drawerMenuItemSelected.getTitle());
        }
    }
```
The fragment is replaced in the content_main.xml layout file using fragmentContent id.
```xml
<FrameLayout
        android:id="@+id/fragmentContent"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```
The first fragment is loaded when the application is lunched:
```java
Fragment fragment = null;
Class fragmentClass = FragmentOne.class;
try {
    fragment = (Fragment) fragmentClass.newInstance();
} catch (Exception e) {
    e.printStackTrace();
}
FragmentManager fragmentManager = getSupportFragmentManager();
fragmentManager.beginTransaction().replace(R.id.fragmentContent, fragment, TAG).commit();
```
### References
* [Using Fragments with the Navigation Drawer Activity by Chris Risner](https://chrisrisner.com/Using-Fragments-with-the-Navigation-Drawer-Activity)
* [Fragment Navigation Drawer by CodePath](https://github.com/codepath/android_guides/wiki/Fragment-Navigation-Drawer)
