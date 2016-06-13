---
layout: page
title: Contacto
permalink: /contacto/
---

<div class="container">
  <form class="form-horizontal">
    <!-- Input group -->
    <div class="form-group has-error">
      <label for="name" class="control-label col-md-3">Nombre:</label>
      <div class="col-md-6">
        <input type="text" class="form-control" name="name" id="name" />
      </div>
    </div>

    <div class="form-group has-error">
      <label for="email" class="control-label col-md-3">Correo electrónico:</label>
      <div class="col-md-6">
        <input type="text" name="email" id="email" class="form-control" />
      </div>
    </div>

    <div class="form-group has-error">
      <label for="message" class="control-label col-md-3">¿Cómo supo de nosotros?:</label>
      <div class="col-md-6">
        <select class="form-control has-error">
          <option>Una amiga/conocida</option>
          <option>Hoja volante</option>
          <option>Búsqueda en internet</option>
          <option>Facebook</option>
          <option>Otro</option>
        </select>
        <!--p class="help-block">Example block-level help text here.</p-->
      </div>
    </div>


    <div class="form-group has-error">
      <label for="message" class="control-label col-md-3">Mensaje:</label>
      <div class="col-md-6">
        <textarea class="form-control" rows="3"></textarea>
      </div>
    </div>

    <!-- Button -->
    <div class="form-group">
      <button type="button" id="submit" class="btn btn-danger col-md-6 col-md-offset-3">Enviar</button>
    </div>
  </form>
</div>

