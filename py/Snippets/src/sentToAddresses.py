#! /usr/bin/python

import email
import getpass
import imaplib

HOST = "imap.example.com"
USER = "alice"
FOLDER = "2006/sent/sent"

connection = imaplib.IMAP4_SSL(HOST)
res, data = connection.login(USER, getpass.getpass())
assert res == "OK"

res, count = connection.select(FOLDER)
assert res == "OK"

res, (msg_nums,) = connection.search(None, "ALL")
assert res == "OK"

for msg_num in msg_nums.split():
    res, message_text = connection.fetch(msg_num, "(RFC822)")
    assert res == "OK"
    
    message = email.message_from_string(message_text[0][1])
    tos = message.get_all("To") or []
    ccs = message.get_all("Cc") or []
    all_recipients = email.Utils.getaddresses(tos + ccs)
    print "\n".join(addr.lower() for realname, addr in all_recipients)
