const search = document.getElementById("search");
const create = document.getElementById("create");
const searchButton = document.getElementById("searchButton");
const createButton = document.getElementById("createButton");
const returnAllBlocksButton = document.getElementById("returnAllBlocksButton");
const tableBody = document.getElementById("table");

searchButton.onclick = () => {
  const blockIndex = search.value;
  fetch(`/node/block/${blockIndex}`)
    .then((response) => response.json())
    .then((data) => {
      tableBody.innerHTML = "";

      const head = document.createElement("tr");

      head.innerHTML = `
        <th>Index</th>
        <th>Timestamp</th>
        <th>Data</th>
        <th>Previous Hash</th>
        <th>Hash</th>
      `;

      tableBody.appendChild(head);

      const row = document.createElement("tr");

      row.innerHTML = `
        <td>${data.index}</td>
        <td>${data.timestamp}</td>
        <td>${data.data}</td>
        <td>${data.previousHash}</td>
        <td>${data.hash}</td>
        `;

      tableBody.appendChild(row);
    })
    .catch((error) => alert("Error:", error));
};

returnAllBlocksButton.onclick = () => {
  fetch("/node/blockchain")
    .then((response) => response.json())
    .then((data) => {
      tableBody.innerHTML = "";

      const head = document.createElement("tr");

      head.innerHTML = `
        <th class="text-center border border-white p-2">Index</th>
        <th class="text-center border border-white p-2">Timestamp</th>
        <th class="text-center border border-white p-2">Data</th>
        <th class="text-center border border-white p-2">Previous Hash</th>
        <th class="text-center border border-white p-2">Hash</th>
      `;

      tableBody.appendChild(head);

      data.forEach((block) => {
        const row = document.createElement("tr");

        row.innerHTML = `
              <td class="border border-white p-2">${block.index}</td>
              <td class="border border-white p-2">${block.timestamp}</td>
              <td class="border border-white p-2">${block.data}</td>
              <td class="border border-white p-2">${block.previousHash}</td>
              <td class="border border-white p-2">${block.hash}</td>
            `;

        tableBody.appendChild(row);
      });
    })
    .catch((error) => alert("Error:", error));
};

createButton.onclick = () => {
  const blockData = create.value;
  fetch("/node/addBlock", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(blockData),
  })
    .then((response) => response.text())
    .then((data) => {
      createButton.disabled = false;
      alert("Bloco criado com sucesso!");
    })
    .catch((error) => {
      createButton.disabled = false;
      alert("Error:", error);
    });
  blockData.value = "";

  alert("Esta é uma ação demorada, aguarde até que um novo alerta apareça");
  createButton.disabled = true;
};
