(ns yaiba.ethers.test-helper.macros
  (:refer-clojure :exclude [slurp]))

;; refer
;; https://gist.github.com/noprompt/9086232
(defmacro slurp [path]
  (clojure.core/slurp path))
