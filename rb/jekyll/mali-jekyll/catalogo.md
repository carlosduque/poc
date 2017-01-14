---
layout: page
title: Catalogo
permalink: /catalogo/
---

{% for project in site.catalogo %}
<div class="project ">
  <div class="thumbnail">
    <a href="{{ site.baseurl }}{{ project.url }}">
    {% if project.img %}
    <img class="thumbnail" src="{{ project.img }}"/>
    {% else %}
    <div class="thumbnail blankbox"></div>
    {% endif %}
    <span>
        <h1>{{ project.title }}</h1>
        <br/>
        <p>{{ project.description }}</p>
    </span>
    </a>
  </div>
</div>

{% endfor %}
