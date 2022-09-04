(ns yaiba.ethers.utils
  (:require ["ethers":as es]
            [oops.core :refer [oget oset! ocall oget+ ocall+ oapply+]]
            [clojure.string :as string]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [camel-snake-kebab.core :as csk]
            [oops.core :refer [oget oset! ocall oget+ ocall+]]
            ))

;; directly borrowed from cljs-web3
;; https://github.com/district0x/cljs-web3/blob/14ba12e37dd944c90f4741d92c8c94394b9a3dba/src/cljs_web3/utils.cljs#L8
(defn safe-case [case-f]
  (fn [x]
    (cond-> (subs (name x) 1)
      true (string/replace "_" "*")
      true case-f
      true (string/replace "*" "_")
      true (->> (str (first (name x))))
      (keyword? x) keyword)))

(def camel-case (safe-case csk/->camelCase))
(def kebab-case (safe-case csk/->kebab-case))

(def js->cljk #(js->clj % :keywordize-keys true))

(def js->cljkk
  "From JavaScript to Clojure with kekab-cased keywords."
  (comp (partial transform-keys kebab-case) js->cljk))

(def cljkk->js
  "From Clojure with kebab-cased keywords to JavaScript."
  (comp clj->js (partial transform-keys camel-case)))

;; some modify
(defn callback-js->clj [x]
  (if (fn? x)
    (fn [err res]
      (when (and res (oget res "v"))
        (oset! res "v" (oget res "v"))) ;; Prevent weird bug in advanced optimisations
      (x err (js->cljkk res)))
    x))

(defn args-callbacks-cljkk->js [args]
  (map (comp cljkk->js callback-js->clj) args))

(defn js-apply-with-callback
  ([this method-name]
   (js-apply-with-callback this method-name nil))
  ([this method-name args]
   (let [method-name (camel-case (name method-name))]
     (if (oget+ this method-name)
       (js->cljkk (ocall+ this method-name (args-callbacks-cljkk->js args)))
       (throw (str "Method: " method-name " was not found in object."))))))

(defn args-cljkk->js
  [args]
  (map cljkk->js args))

;;;;;;



(defn promise-fn
  [on-success-fn on-error-fn]
  (fn [object method-name args]
    (let [method-name (camel-case (name method-name))]
      (if (oget+ object method-name)
        (-> (oapply+ object method-name (args-cljkk->js args))
            (.then on-success-fn)
            (.catch on-error-fn))
        (throw (str "Method: " method-name " was not found in object"))))))

(defn promise-call
  [object method-name on-success-fn on-error-fn args]
  (let [method-name (camel-case (name method-name))]
    (if (oget+ object method-name)
      (-> (oapply+ object method-name (args-cljkk->js args))
          (.then on-success-fn)
          (.catch on-error-fn))
      (throw (str "Method: " method-name " was not found in object.")))))

;;;;;;;;;;;;;;;;;;;;;;;;;;  ethers.utils


(def _utils (oget es "utils"))

(defn contract-address
  [{:keys [from nonce] :as tx}]
  (ocall _utils "getContractAddress" (cljkk->js tx)))

(defn to-readable-abi [json-abi]
  (-> json-abi
      (es/utils.Interface.)
      (.format es/utils.FormatTypes.-full)))
