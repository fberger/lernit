from google.appengine.ext import db

class Entry(db.Expando):
    entry = db.StringProperty(multiline=True)
    translation = db.StringProperty(multiline=True)
    creation_date = db.DateTimeProperty(auto_now_add=True)
    category = db.StringProperty(choices=set(["noun", "verb", "adjective",
                                              "adverb", "phrase"])
    entry_lang = db.StringProperty()
    translation_lang = db.StringProperty()
    
