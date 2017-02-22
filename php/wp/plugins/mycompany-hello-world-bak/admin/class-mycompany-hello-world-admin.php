<?php
/**
 * The admin-specific functionality of the plugin.
 *
 * @link       http://mycompany.com
 * @since      1.0.0
 *
 * @package    MyCompany_Hello_World
 * @subpackage MyCompany_Hello_World/admin
 */
/**
 * The admin-specific functionality of the plugin.
 *
 * Defines the plugin name, version, and two examples hooks for how to
 * enqueue the admin-specific stylesheet and JavaScript.
 *
 * @package    MyCompany_Hello_World
 * @subpackage MyCompany_Hello_World/admin
 * @author     Your Name <email@example.com>
 */
class MyCompany_Hello_World_Admin {
    /**
     * The ID of this plugin.
     *
     * @since    1.0.0
     * @access   private
     * @var      string    $plugin_name    The ID of this plugin.
     */
    private $plugin_name;

    /**
     * The version of this plugin.
     *
     * @since    1.0.0
     * @access   private
     * @var      string    $version    The current version of this plugin.
     */
    private $version;

    /**
     * Initialize the class and set its properties.
     *
     * @since    1.0.0
     * @param      string    $plugin_name       The name of this plugin.
     * @param      string    $version    The version of this plugin.
     */
    public function __construct( $plugin_name, $version ) {
        $this->plugin_name = $plugin_name;
        $this->version = $version;
    }

    /**
     * Register the stylesheets for the admin area.
     *
     * @since    1.0.0
     */
    public function enqueue_styles() {
        /**
         * This function is provided for demonstration purposes only.
         *
         * An instance of this class should be passed to the run() function
         * defined in Plugin_Name_Loader as all of the hooks are defined
         * in that particular class.
         *
         * The Plugin_Name_Loader will then create the relationship
         * between the defined hooks and the functions defined in this
         * class.
         */
        wp_enqueue_style( $this->plugin_name, plugin_dir_url( __FILE__ ) . 'css/mycompany-hello-world-admin.css', array(), $this->version, 'all' );
    }

    /**
     * Register the JavaScript for the admin area.
     *
     * @since    1.0.0
     */
    public function enqueue_scripts() {
        /**
         * This function is provided for demonstration purposes only.
         *
         * An instance of this class should be passed to the run() function
         * defined in Plugin_Name_Loader as all of the hooks are defined
         * in that particular class.
         *
         * The Plugin_Name_Loader will then create the relationship
         * between the defined hooks and the functions defined in this
         * class.
         */
        wp_enqueue_script( $this->plugin_name, plugin_dir_url( __FILE__ ) . 'js/mycompany-hello-world-admin.js', array( 'jquery' ), $this->version, false );
    }

    // This just echoes the chosen line, we'll position it later
    public function talk_to_me() {
        $chosen = $this->get_lyric();
        echo "<p id='lyrics'>$chosen</p>";
    }

    private function get_lyric() {
        /** These are the lyrics to Hello Dolly */
        $lyrics = "Tamatoa Cangrejo gris pequeñin fue
            Soy feliz como una almeja más que ayer
            Pues soy hermoso, ves baby
            Tienes que escuchar a tu corazón
            Así te dijo la anciana
            En dos palabras voy su frase a destrozar
            Te engañó
            Prefiero mi brillo
            Cual tesoro del pirata que se hundió
            Limpia ahí, y muestra su Brillo
            Brillaré como el cuellito de un bebe
            Ay ya sé ¿Tu qué crees?
            Peces bobos son
            Seguirán lo que les brille, Novatos
            Oh, y aquí vienen ya, por la luz que les da brillo
            Mmm, pesquisa
            Comida gratis
            Y tú eres el postre";

        // Here we split it into lines
        $lyrics = explode( "\n", $lyrics );

        // And then randomly choose a line
        return wptexturize( $lyrics[ mt_rand( 0, count( $lyrics ) - 1 ) ] );
    }

}
