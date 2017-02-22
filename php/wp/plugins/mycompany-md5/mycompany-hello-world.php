<?php
/**
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
 * @package WordPress
 * @author  Carlos Duque
 */
if (!defined('ABSPATH')) {
    die; // Exit if accessed directly.
}
if (!class_exists('MyCompany_Hello_World')) {
    /**
     * Main Class.
     */
    class MyCompany_Hello_World {
        /**
        * Plugin version.
        *
        * @var string
        */
        const VERSION = '1.0.0';
        /**
         * Instance of this class.
         *
         * @var object
         */
        protected static $instance = null;
        /**
         * Return an instance of this class.
         *
         * @return object single instance of this class.
         */
        public static function get_instance() {
            if ( null === self::$instance ) {
                self::$instance = new self;
            }
            return self::$instance;
        }
        /**
         * Constructor
         */
        private function __construct() {
            if (!class_exists('WooCommerce')) {
                add_action( 'admin_notices', array( $this, 'fallback_notice' ) );
            } else {
                $this->load_plugin_textdomain();
                $this->includes();
            }
        }
        /**
         * Method to includes our dependencies.
         *
         * @var string
         */
        public function includes() {
            include_once 'includes/hello-world.php';
        }
        /**
         * Load the plugin text domain for translation.
         * https://codex.wordpress.org/I18n_for_WordPress_Developers
         *
         * @access public
         * @return bool
         */
        public function load_plugin_textdomain() {
            $locale = apply_filters( 'wepb_plugin_locale', get_locale(), 'mycompany-hello-world' );
            load_plugin_textdomain('mycompany-hello-world', false, dirname(plugin_basename(__FILE__)) . '/languages');
            return true;
        }
        /**
         * Fallback notice.
         *
         * We need some plugins to work, and if any isn't active we'll show you!
         */
        public function fallback_notice() {
            //echo '<div class="error">';
            //echo '<p>' . __( 'MyCompany Hello World: Needs the WooCommerce Plugin activated.', 'mycompany-hello-world' ) . '</p>';
            //echo '</div>';

            //$css_class = 'error notice-error is-dismissible';
            $css_class = 'error';
            $message = __( 'MyCompany Hello World: Needs the WooCommerce Plugin activated.', 'mycompany-hello-world' );
            printf('<div class="%1$s><p>%2$s</p></div>', $css_class, $message);
        }
    }
}
/**
* Initialize the plugin.
*/
add_action( 'plugins_loaded', array( 'MyCompany_Hello_World', 'get_instance' ) );
