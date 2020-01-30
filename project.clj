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
  :dependencies [
                 [origami/optimized-image-enhanced "1.4"]
                 [org.clojure/clojure "1.8.0"]
                 [info.picocli/picocli "4.1.0"]
                 [origami/origami "4.2.0-0"]])