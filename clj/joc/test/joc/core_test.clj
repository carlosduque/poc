(ns joc.core-test
  (:require [clojure.test :refer :all]
            [joc.core :refer :all]
            [clojure.tools.cli :refer [parse-opts]))

(comment (deftest pairs-of-values
  (let [args ["--server" "localhost"
              "--port" "8080"
              "--environment" "production"]]
    (is (= {:server "localhost"
            :port "8080"
            :environment "production"}
           (parse-args args))))))

(deftest parse-cli-options
  (let [args ["--port" "24090"
              "-v"
              "--xxx okeydokey"]]
    (is (= {:options
            {:port 24090
             :verbosity 1
             :xxx :okeydokey}}
           (parse-opts args cli-options)))))
