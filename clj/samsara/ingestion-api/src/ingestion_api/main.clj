(ns ingestion-api.main
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.tools.cli :refer [parse-opts]]
            [com.stuartsierra.component :as component]
            [ingestion-api.system :refer [ingestion-api-system]]
            [samsara.trackit :refer [set-base-metrics-name! start-reporting!]]
            [samsara.utils :refer [log-fmt-fn]]
            [synapse.synapse :refer [load-config-file!]]
            [taoensso.timbre :as log]))


(def DEFAULT-CONFIG
  "Default configuration which will be merged with
  the user defined configuration."
  {:server {:port 9000 :auto-reload false}
   :admin-server {:port 9010 :auto-reload false}
   :mqtt   {:port 10010 :enabled true}

   :log   {:timestamp-opts
           {:pattern     "yyyy-MM-dd HH:mm:ss.SSS zzz"
            :locale      (java.util.Locale. "en")
            :timezone    (java.util.TimeZone/getDefault)}}

   :backend  {:type :console :pretty? true}
   ;;:backend {:type :kafka :topic "ingestion" :metadata.broker.list "192.168.59.103:9092"}

   :tracking {:enabled false :type :console}
   })

(def cli-options
  "Command line options"
  [
   ["-c" "--config FILE" "Config file (.edn)"
    :validate [#(.exists (io/file %)) "The given file must exist"]]

   ["-h" "--help"]
   ])


(defn message
  "Prints a message in the stderr channel"
  [& m]
  (binding [*out* *err*]
    (apply println m)))


(defn version
  "Reads the version from the project.clj file
  which is typically boundled with jar file"
  []
  (try
    (->> (if (.exists (io/file "../samsara.version"))
           (io/file "../samsara.version")
           (io/resource "samsara.version"))
         slurp
         (str "v"))
    (catch Exception x "")))


(defn- headline
  "Return a string with the headline and the version number"
  []
  (format
   "
-----------------------------------------------
   _____
  / ___/____ _____ ___  _________ __________ _
  \\__ \\/ __ `/ __ `__ \\/ ___/ __ `/ ___/ __ `/
 ___/ / /_/ / / / / / (__  ) /_/ / /  / /_/ /
/____/\\__,_/_/ /_/ /_/____/\\__,_/_/   \\__,_/

  Samsara Ingestion API
--------------------------| %10.10s |-------

" (version)))


(defn- help!
  "Pritns a help message"
  [errors]
  (let [err-msg (if-not errors "" (clojure.string/join "\n" errors))]
    (message
     (format
      "%s%s

SYNOPSIS
       ./ingestion-api -c config.edn

DESCRIPTION
       Starts a REST interface which accepts events
       and sends them to Samsara's processing pipeline.

  OPTIONS:

  -c --config config-file.edn [REQUIRED]
       Configuration file to set up the REST interface
       example: './config/config.edn'

  -h --help
       this help page

" (headline) err-msg))))


(defn exit!
  "System exit with error state"
  [n]
  (System/exit n))


(defn- read-config
  "Read the user's configuration file
  and apply the default values."
  [config-file]
  (->> (load-config-file! config-file)
       (merge-with merge DEFAULT-CONFIG)))



(defn- init-log!
  "Initializes log settings"
  [cfg]
  (log/swap-config! assoc :output-fn log-fmt-fn)
  (log/merge-config! cfg))



(defn- init-tracking!
  "Initialises the metrics tracking system"
  [{enabled :enabled :as cfg}]
  (when enabled
    (log/info "Sending metrics to:" cfg)
    (set-base-metrics-name! "samsara" "ingestion")
    (start-reporting! cfg)))


(defn init!
  "Initializes the system and returns the actual configuration used."
  [config-file]
  (let [config (read-config config-file)]

    (init-log!      (:log config))
    (init-tracking! (:tracking config))

    config))


(defn -main
  [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options)            (do (help! nil) (exit! 1))
      (nil? (:config options))   (do (help! ["ERROR: Missing configuration."]) (exit! 1))
      errors                     (do (help! errors) (exit! 1))

      :default
      (let [_ (println (headline))
            {config-file :config} options
            config (init! config-file)]
        (component/start
         (ingestion-api-system config))
        (println "\nSamsara's ingestion-api is ready.!!!\n")
        @(promise)))))
