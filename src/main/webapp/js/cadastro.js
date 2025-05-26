const contextPath = window.location.pathname.split('/')[1]
    ? '/' + window.location.pathname.split('/')[1]
    : '';

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("cadastroForm");

  form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    try {
      const response = await fetch(`${contextPath}/cadastraUsuario`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        const usuarioDto = await response.json();
        alert("Cadastro realizado com sucesso! Faça login.");
        window.location.href = `${contextPath}/login.jsp`;
      } else {
        const errorResp = await response.json();
        alert("Erro no cadastro: " + (errorResp.mensagem || "Erro desconhecido"));
      }
    } catch (error) {
      alert("Erro ao conectar com o servidor.");
      console.log(error);
    }
  });
});
