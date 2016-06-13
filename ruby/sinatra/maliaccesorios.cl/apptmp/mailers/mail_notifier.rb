class MailNotifier < ApplicationMailer
  default from: 'Mali Accesorios <info@maliaccesorios.cl>'
  # Subject can be set in your I18n file at config/locales/en.yml
  # with the following lookup:
  #
  #   en.mail_notifier.contact.subject
  #
  def contact
    @greeting = I18n.t 'hello'

    mail to: "to@example.org", subject: 'Mail received'
  end
end
