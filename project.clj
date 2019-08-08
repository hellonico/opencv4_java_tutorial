(defproject cv4.tutorial "0.1.0-SNAPSHOT"
  :java-source-paths ["java"]
  :main HelloCv
  :plugins [[lein-auto "0.1.3"]]
  :source-paths ["clj"]
  :aliases {
            "catwalk.oneimage" ["run" "-m" "rt.catwalk.oneimage"]
            "catwalk.videotoscreen" ["run" "-m" "rt.catwalk.videotoscreen"]
            "catwalk.videotofile" ["run" "-m" "rt.catwalk.videotofile"]}

  :auto {:default {:file-pattern #"\.(java)$"}}
  :repositories [["vendredi" "https://repository.hellonico.info/repository/hellonico/"]]
  :dependencies [[org.clojure/clojure "1.8.0"] [origami/origami "4.1.1-1"]])