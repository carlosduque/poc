<?php
/**
 * MyCompany_Hello_World
 *
 * @category  Class
 * @package   MyCompany_Hello_World
 * @subpackage   MyCompany_Hello_World/includes
 * @author    Carlos Duque
 * @license   http://www.gnu.org/copyleft/gpl.html GNU General Public License
 * @link      https://mycompany.com
 */
if (!defined('ABSPATH')) {
    die; // Exit if accessed directly.
}
/**
 * Class to ???.
 */
class MyCompany_Hello_World {
    /**
     * The unique identifier of this plugin.
     *
     * @since    1.0.0
     * @access   protected
     * @var      string    $plugin_name    The string used to uniquely identify this plugin.
     */
    protected $plugin_name;

    /**
     * Plugin version.
     *
     * @var string
     */
    const VERSION = '1.0.0';

    /**
     * The array of actions registered with WordPress.
     *
     * @since    1.0.0
     * @access   protected
     * @var      array    $actions    The actions registered with WordPress to fire when the plugin loads.
     */
    protected $actions;

    /**
     * The array of filters registered with WordPress.
     *
     * @since    1.0.0
     * @access   protected
     * @var      array    $filters    The filters registered with WordPress to fire when the plugin loads.
     */
    protected $filters;

    /**
     * Constructor
     */
    public function __construct() {
        $this->plugin_name = 'mycompany-hello-world';
        $this->actions = array();
        $this->filters = array();

        $this->load_dependencies();
        $this->set_locale();
        $this->define_admin_hooks();
        $this->define_public_hooks();
    }

    /**
     * Register the plugin.
     *
     * @since    1.0.0
     */
    public function register() {
        //TODO: add a $dependencies array to be traversed and checked beforehand
        if (!class_exists('WooCommerce')) {
            add_action( 'admin_notices', array( $this, 'fallback_notice' ) );
        } else {
            foreach ( $this->filters as $hook ) {
                add_filter( $hook['hook'], array( $hook['component'], $hook['callback'] ), $hook['priority'], $hook['accepted_args'] );
            }

            foreach ( $this->actions as $hook ) {
                add_action( $hook['hook'], array( $hook['component'], $hook['callback'] ), $hook['priority'], $hook['accepted_args'] );
            } 
        }
    }

    /**
     * The name of the plugin used to uniquely identify it within the context of
     * WordPress and to define internationalization functionality.
     *
     * @since     1.0.0
     * @return    string    The name of the plugin.
     */
    public function get_plugin_name() {
        return $this->plugin_name;
    }

    /**
     * Add a new action to the collection to be registered with WordPress.
     *
     * @since    1.0.0
     * @param    string               $hook             The name of the WordPress action that is being registered.
     * @param    object               $component        A reference to the instance of the object on which the action is defined.
     * @param    string               $callback         The name of the function definition on the $component.
     * @param    int                  $priority         Optional. he priority at which the function should be fired. Default is 10.
     * @param    int                  $accepted_args    Optional. The number of arguments that should be passed to the $callback. Default is 1.
     */
    public function register_action( $hook, $component, $callback, $priority = 10, $accepted_args = 1 ) {
        $this->actions = $this->add( $this->actions, $hook, $component, $callback, $priority, $accepted_args );
    }

    /**
     * Add a new filter to the collection to be registered with WordPress.
     *
     * @since    1.0.0
     * @param    string               $hook             The name of the WordPress filter that is being registered.
     * @param    object               $component        A reference to the instance of the object on which the filter is defined.
     * @param    string               $callback         The name of the function definition on the $component.
     * @param    int                  $priority         Optional. he priority at which the function should be fired. Default is 10.
     * @param    int                  $accepted_args    Optional. The number of arguments that should be passed to the $callback. Default is 1
     */
    public function register_filter( $hook, $component, $callback, $priority = 10, $accepted_args = 1 ) {
        $this->filters = $this->add( $this->filters, $hook, $component, $callback, $priority, $accepted_args );
    }

    /**
     * Fallback notice.
     *
     * We need some plugins to work, and if any isn't active we'll show you!
     */
    private function fallback_notice() {
        //echo '<div class="error">';
        //echo '<p>' . __( 'MyCompany Hello World: Needs the WooCommerce Plugin activated.', 'mycompany-hello-world' ) . '</p>';
        //echo '</div>';

        //$css_class = 'error notice-error is-dismissible';
        $css_class = 'error';
        $message = __( ': Needs the WooCommerce Plugin activated.', 'mycompany-hello-world' );
        printf('<div class="%1$s><p>%2$s</p></div>', $css_class, $message);
    }

    /**
     * Load the required dependencies for this plugin.
     *
     * Include the following files that make up the plugin:
     *
     * - MyCompany_Hello_World_Loader. Orchestrates the hooks of the plugin.
     * - MyCompany_Hello_World_i18n. Defines internationalization functionality.
     * - MyCompany_Hello_World_Admin. Defines all hooks for the admin area.
     * - MyCompany_Hello_World_Public. Defines all hooks for the public side of the site.
     *
     * Create an instance of the loader which will be used to register the hooks
     * with WordPress.
     *
     * @since    1.0.0
     * @access   private
     */
    private function load_dependencies() {
        /**
         * The class responsible for orchestrating the actions and filters of the
         * core plugin.
         */
        //require_once plugin_dir_path( dirname( __FILE__ ) ) . 'includes/class-mycompany-hello-world-loader.php';

        /**
         * The class responsible for defining internationalization functionality
         * of the plugin.
         */
        require_once plugin_dir_path( dirname( __FILE__ ) ) . 'includes/class-mycompany-hello-world-i18n.php';

        /**
         * The class responsible for defining all actions that occur in the admin area.
         */
        require_once plugin_dir_path( dirname( __FILE__ ) ) . 'admin/class-mycompany-hello-world-admin.php';
        /**
         * The class responsible for defining all actions that occur in the public-facing
         * side of the site.
         */
        require_once plugin_dir_path( dirname( __FILE__ ) ) . 'public/class-mycompany-hello-world-public.php';
    }

    /**
     * Define the locale for this plugin for internationalization.
     *
     * @since    1.0.0
     * @access   private
     */
    private function set_locale() {
        $plugin_i18n = new MyCompany_Hello_World_I18n();
        $this->register_action( 'plugins_loaded', $plugin_i18n, 'load_translations' );
    }

    /**
     * Register all of the hooks related to the admin area functionality
     * of the plugin.
     *
     * @since    1.0.0
     * @access   private
     */
    private function define_admin_hooks() {
        $plugin_admin = new MyCompany_Hello_World_Admin( $this->plugin_name, self::VERSION );
        $this->register_action( 'admin_enqueue_scripts', $plugin_admin, 'enqueue_styles' );
        $this->register_action( 'admin_enqueue_scripts', $plugin_admin, 'enqueue_scripts' );

        $this->register_action( 'admin_notices', $plugin_admin, 'talk_to_me' );
    }

    /**
     * Register all of the hooks related to the public-facing functionality
     * of the plugin.
     *
     * @since    1.0.0
     * @access   private
     */
    private function define_public_hooks() {
        $plugin_public = new MyCompany_Hello_World_Public( $this->plugin_name, self::VERSION );
        $this->register_action( 'wp_enqueue_scripts', $plugin_public, 'enqueue_styles' );
        $this->register_action( 'wp_enqueue_scripts', $plugin_public, 'enqueue_scripts' );
    }

    /**
     * A utility function that is used to register the actions and hooks into a single
     * collection.
     *
     * @since    1.0.0
     * @access   private
     * @param    array                $hooks            The collection of hooks that is being registered (that is, actions or filters).
     * @param    string               $hook             The name of the WordPress filter that is being registered.
     * @param    object               $component        A reference to the instance of the object on which the filter is defined.
     * @param    string               $callback         The name of the function definition on the $component.
     * @param    int                  $priority         The priority at which the function should be fired.
     * @param    int                  $accepted_args    The number of arguments that should be passed to the $callback.
     * @return   array                                  The collection of actions and filters registered with WordPress.
     */
    private function add( $hooks, $hook, $component, $callback, $priority, $accepted_args ) {
        $hooks[] = array(
            'hook'          => $hook,
            'component'     => $component,
            'callback'      => $callback,
            'priority'      => $priority,
            'accepted_args' => $accepted_args
        );
        return $hooks;
    }
}
