<?php
/**
 * Fired during plugin activation
 *
 * @link       http://mycompany.com
 * @since      1.0.0
 *
 * @package    MyCompany_Hello_World
 * @subpackage MyCompany_Hello_World/includes
 */

/**
 * Define the internationalization functionality.
 *
 * Loads and defines the internationalization files for this plugin
 * so that it is ready for translation.
 *
 * @since      1.0.0
 * @package    MyCompany_Hello_World
 * @subpackage MyCompany_Hello_World/includes
 * @author     Your Name <email@example.com>
 */
class MyCompany_Hello_World_I18n {
    /**
     * Load the plugin text domain for translation.
     *
     * @since    1.0.0
     */
    public function load_translations() {
        load_plugin_textdomain('mycompany-hello-world', false, dirname( plugin_basename( __FILE__ ) ) . '/languages/' );
    }
}
