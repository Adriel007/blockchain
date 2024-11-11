document.addEventListener("DOMContentLoaded", function () {
  const acessWalletButton = document.getElementById("accessWallet");
  const createWalletButton = document.getElementById("createWallet");
  createWalletButton.onclick = () => {
    const walletName = document.getElementById("walletName").value;
    const walletPassword = document.getElementById("walletPassword").value;

    if (walletName && walletPassword) {
      const hashedPassword = sha256(walletPassword);
      fetch("/node/createWallet", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: walletName,
          password: hashedPassword,
        }),
      })
        .then((response) => response.json())
        .then((data) => {
          alert(
            "Wallet criada com sucesso!\nSua frase de recuperação: " +
              data.mnemonic
          );
        })
        .catch((error) => console.error("Erro ao criar wallet:", error));
    } else {
      alert("Por favor, preencha todos os campos.");
    }
  };

  acessWalletButton.onclick = () => {
    const walletName = document.getElementById("walletName").value;
    const walletPassword = document.getElementById("walletPassword").value;

    if (walletName && walletPassword) {
      const hashedPassword = sha256(walletPassword);

      fetch("/node/accessWallet", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: walletName,
          password: hashedPassword,
        }),
      })
        .then((response) => response.json())
        .then((data) => {
          if (data.success) {
            alert("Acesso à wallet bem-sucedido!");
            console.log("Informações da Wallet:", data.walletInfo);
          } else {
            alert("Falha ao acessar a wallet. Verifique as credenciais.");
          }
        })
        .catch((error) => console.error("Erro ao acessar wallet:", error));
    } else {
      alert("Por favor, preencha todos os campos.");
    }
  };

  function sha256(str) {
    return crypto.subtle
      .digest("SHA-256", new TextEncoder().encode(str))
      .then((hash) => {
        return Array.from(new Uint8Array(hash))
          .map((b) => b.toString(16).padStart(2, "0"))
          .join("");
      });
  }
});
