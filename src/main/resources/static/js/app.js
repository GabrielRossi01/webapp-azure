const modalDeletar = document.getElementById('modalDeletar');
if (modalDeletar) {
  modalDeletar.addEventListener('show.bs.modal', function (event) {
    const btn = event.relatedTarget;
    document.getElementById('nomeItem').textContent = btn.getAttribute('data-nome');
    document.getElementById('btnConfirmarDeletar').setAttribute('href', btn.getAttribute('data-url'));
  });
}

document.querySelectorAll('.alert-dismissible').forEach(function(el) {
  setTimeout(function() {
    const bsAlert = bootstrap.Alert.getOrCreateInstance(el);
    bsAlert.close();
  }, 4000);
});
