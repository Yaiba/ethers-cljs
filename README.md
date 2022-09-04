# ethers-cljs
Ethers.js library in clojurescript.
Inspired by [cljs-web3-next](https://github.com/district0x/cljs-web3-next)

## smart contract setup
This project use [ape](https://github.com/ApeWorX/ape.git) and [vyper](https://github.com/vyperlang/vyper.git) to craft smart contract. 

YToken is directly from the [ape ERC20 template](https://github.com/ApeAcademy/token-template)

```shell
# init ape
pip install -r requirements.txt
ape plugins install .

# start a local testnet
ape console --verbosity DEBUG
# compile & deploy contract in another terminal
ape run deploy --network ethereum:local:ganache
```

## run test
In order to test, a local testnet is needed, you need go through [smart contract section](#smart-contract-setup).

After you setup local testnet, run: `npm run watch`
