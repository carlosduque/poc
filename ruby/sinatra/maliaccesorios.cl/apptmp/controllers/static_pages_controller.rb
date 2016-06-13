class StaticPagesController < ApplicationController
  def inicio
  end

  def acerca_de
  end

  def contacto
    MailNotifier.contact.deliver
  end

  def cintillos
  end

  def colet
  end

  def bebe
  end

  def disenios
  end

  def solidos
  end

  def ballet
  end

  def colegiales
  end

  def princesas
  end

  def estacionales
  end

  def fiestas_patrias
  end

  def navidad
  end

  def pascua
  end

  def san_valentin
  end

end
