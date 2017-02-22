<?php
/**
 * The plugin bootstrap file
 *
 * This file is read by WordPress to generate the plugin information in the plugin
 * admin area. This file also includes all of the dependencies used by the plugin,
 * registers the activation and deactivation functions, and defines a function
 * that starts the plugin.
 *
 * @link              http://mycompany.com
 * @since             1.0.0
 * @package           MyCompany_Hello_World
 * @author  Carlos Duque
 *
 * @wordpress-plugin
 * Plugin Name: MyCompany Hello World
 * Version: 1.0.0
 * Plugin URI:  https://mycompany.com/plugins/the-basics/
 * Description: Basic WordPress Plugin Header Comment for MyCompany Hello World Plugin
 * Author:      Carlos Duque
 * Author URI:  https://mycompany.com/
 * License:     GPL2
 * License URI: https://www.gnu.org/licenses/gpl-2.0.html
 * Requires at least: 4.4.0
 * Tested up to: 4.6.0
 *
 * Text Domain: mycompany-hello-world
 * Domain Path: /languages
 *
 */

if (! function_exists('add_action' ) ) {
    echo 'I can\'t do much when called direcly, I\'m just a plugin man !';
    die;
}

/**
 * The code that runs during plugin activation.
 * This action is documented in includes/class-plugin-name-activator.php
 */
function activate_plugin_name() {
    require_once plugin_dir_path( __FILE__ ) . 'includes/class-mycompany-hello-world-activator.php';
    MyCompany_Hello_World_Activator::activate();
}

/**
 * The code that runs during plugin deactivation.
 * This action is documented in includes/class-plugin-name-deactivator.php
 */
function deactivate_plugin_name() {
    require_once plugin_dir_path( __FILE__ ) . 'includes/class-mycompany-hello-world-deactivator.php';
    MyCompany_Hello_World_Deactivator::deactivate();
}

/**
 * register activation/deactivation hooks
 */
register_activation_hook( __FILE__, 'activate_plugin_name' );
register_deactivation_hook( __FILE__, 'deactivate_plugin_name' );

/**
 * The core plugin class that is used to define internationalization,
 * admin-specific hooks, and public-facing site hooks.
 */
require plugin_dir_path( __FILE__ ) . 'includes/class-mycompany-hello-world.php';

/**
 * Begins execution of the plugin.
 *
 * Since everything within the plugin is registered via hooks,
 * then kicking off the plugin from this point in the file does
 * not affect the page life cycle.
 *
 * @since    1.0.0
 */
function register_mycompany_hello_world() {
    $plugin = new MyCompany_Hello_World();
    $plugin->register();
}

register_mycompany_hello_world();

