(ns depends.core
  (use [clojure.java.io])
  (:gen-class :main true))

;; Accept a directory as a command line argument
;; Walk directory to find all project.clj files
;; For each file extract the dependencies list
;; Produce a report showing each project and it's dependencies

(defn walk 
  "Return a list of files found in all subdirectories that match pattern
  @see http://rosettacode.org/wiki/Walk_a_directory/Recursively#Clojure"
  [dirpath pattern]
  (doall (filter #(re-matches pattern (.getName %))
                 (file-seq (file dirpath)))))

(defn project-files 
  "Find all project.clj files in subdirectories of dirpath
   returns java.io.File instances"
  [dirpath]
  (walk dirpath #"project\.clj"))

(defn file-dependencies 
  "For a given java.io.file return it's dependencies by extracting the :dependencies
  return [[library1 version] [library2 version2] ...]"
  [file]
  (let [deps (:dependencies 
              (->> (.getPath file) slurp 
                   read-string 
                   ;; drop defproject name and version
                   (drop 3) 
                   ;; build pairs :foo "string"
                   (partition 2) 
                   ;; put into vectors
                   (map vec) 
                   ;; insert into hashmap
                   (into {})))]
    ;;(apply concat (map (fn [x] [x file-path])) deps)
    (sort deps)))

(defn report [dirpath]
  ;; (map (fn [[library version]][library version (.getPath (file sample-file-path))]) (file-dependencies (file sample-file-path)))

  (let [project-files (project-files dirpath)]
    (sort (apply concat
                 (map
                  #(map (fn [[library version]] [library version (.getPath (file %))]) (file-dependencies %))
                  project-files)))))

(defn create-org-mode-file-report [dirpath reportpath]
  (let [r (report dirpath)
        p (fn [[a b c ]] (println "|" a "|" b "|" c "|"))]
    (map p r)))


(defn -main [& args]
  (let [dir (if args (first args) ".")]
    (println dir)
    ;; (println (System/getProperty "user.dir"))
    (doseq [r (report dir)] (println r))))
