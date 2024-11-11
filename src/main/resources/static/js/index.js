const iframe = document.getElementById("iframe");

const aboutButton = document.getElementById("aboutButton");
const walletButton = document.getElementById("walletButton");
const blockchainButton = document.getElementById("blockchainButton");
const accountButton = document.getElementById("accountButton");

aboutButton.onclick = () => (iframe.src = "./html/about.html");
walletButton.onclick = () => (iframe.src = "./html/wallet.html");
blockchainButton.onclick = () => (iframe.src = "./html/blockchain.html");
accountButton.onclick = () => (iframe.src = "./html/account.html");
