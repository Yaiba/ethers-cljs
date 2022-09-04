(ns yaiba.ethers.node-test
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [oops.core :refer [ocall oget oget+]]
            [cljs.core.async :refer [go]]
            [cljs.core.async.interop :refer-macros [<p!]]
            [yaiba.ethers.core :as ye]
            [yaiba.ethers.contract :as yec]
            [yaiba.ethers.provider :as yep]
            [yaiba.ethers.utils :as yeu]
            [yaiba.ethers.test-helper.smart-contract :refer [smart-contracts account]]
            [yaiba.ethers.test-helper.web3 :as web3]
            ["ethers" :as es]))

(enable-console-print!)

(deftest test-running-env
  (is (= (web3/running-in-browser?) false)))

(deftest test-ethers-provider-read
  (let [provider (web3/get-provider)
        owner (:owner account)
        erc20-address (-> smart-contracts :erc20 :address)
        erc20-abi (-> smart-contracts :erc20 :abi)]
    (async done
           (go
             (let [
                   ;; raw rpc request
                   chain-id (<p! (yep/rpc-request provider "eth_chainId"))
                   accounts (<p! (yep/rpc-request provider "eth_accounts"))
                   ;;
                   network (<p! (yep/get-network provider))
                   block-number (<p! (ocall provider "getBlockNumber"))
                   block-number2 (<p! (yep/get-block-number provider))
                   gas-price (<p! (yep/get-gas-price provider))
                   ;; read-only contract methods
                   erc20-code (<p! (yep/get-code provider erc20-address))
                   erc20-contract (yec/contract provider erc20-address erc20-abi)
                   erc20-name (<p! (yec/call erc20-contract "name"))
                   erc20-symbol (<p! (yec/call erc20-contract "symbol"))
                   erc20-decimals (<p! (yec/call erc20-contract "decimals"))
                   erc20-owner (<p! (yec/call erc20-contract "owner"))
                   erc20-total-supply (<p! (yec/call erc20-contract "totalSupply"))
                   erc20-balance-of-owner (<p! (yec/call erc20-contract "balanceOf" owner))
                   ]

               (is (string? chain-id))
               (is (array? accounts))
               (is (= 5 (count accounts)))
               (is (object? network))
               (is (int? (oget network "chainId")))
               (is (int? block-number))
               (is (int? block-number2))
               (is (oget gas-price "_isBigNumber"))

               (is (= "YToken" erc20-name))
               (is (= "YT" erc20-symbol))
               (is (= 18 erc20-decimals))
               (is (= owner erc20-owner))
               (is (= (ocall (ye/to-big-number 1000) "toString") (str erc20-total-supply)))
               (is (= "1000" (str erc20-balance-of-owner)))
               ;; non exists contract method
               (is (thrown? js/Error (yec/call erc20-contract "nonExistsMethod")))

               (done))))))


(deftest test-ethers-provider-write
  (let [provider (web3/get-provider)
        signer (yep/get-signer provider)
        owner (:owner account)
        erc20-address (-> smart-contracts :erc20 :address)
        erc20-abi (-> smart-contracts :erc20 :abi)]
    (async done
           (go
             (let [
                   ;; raw rpc request

                   ;; read-only contract methods

                   erc20-contract (yec/contract signer erc20-address erc20-abi)
                   ]

               (done))))))


