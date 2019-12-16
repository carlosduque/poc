#!/usr/bin/python
import csv, string

def creditdebit(credit, debit):
	if credit != '':
		return credit
	if debit != '':
		return '-' + debit;
	raise Exception

def cleanrow(row):
	newrow = []
	for cell in row:
		cell = string.strip(cell, '\xa0')
		newrow.append(cell)
	return newrow

def reverse(str):
	strlist = list(str)
	strlist.reverse()
	return ''.join(strlist)

# Proper transliteration table, this is limited since there are no moving letters in hebrew by default
hebrew_letters = '\xe0\xe1\xe2\xe3\xe4\xe5\xe6\xe7\xe8\xe9\xea\xeb\xec\xed\xee\xef\xf0\xf1\xf2\xf3\xf4\xf5\xf6\xf7\xf8\xf9\xfa"'
english_trans = "^bgdhwzx@ykklmmnns&ppccqr$t'"
heb2eng_table = string.maketrans(hebrew_letters, english_trans)

# Word translation to add moving letters
hebrew_words = {
	'pqdwn': 'pikadon',
	'mpikadon': 'mepikadon',
	'mpqdwn': 'mepikadon',
	"hw'q": 'horaat keva',
	"qwpt'g": 'qupat gemel',
	'ms@rqrd': 'mastercard',
	'$yq': 'check',
	'pr&wn': 'peraon',
	'rybyt': 'ribit',
	'm$kwrt': 'Salary',
	"pr'y-hpqdh-n@": 'Pery deposit',
	"pr'y-m$ykh-n@": 'Perry withdrawal',
	'dmy r$wm p&wlh': 'Dmei Rishum Peula',
	'mkby': 'Maccabi health',
	"ny'&-qnyh-n@": 'Stock Purchase',
	"ny'&-pdywn": 'Stock Redeem',
	'm$ykt pikadon-n@': 'Pikadon Withdrawal',
	"m$ykh mbnq-q@": 'Cash Withdrawal',
	"m@'x mzwmnym": 'Foreign Cash Withdrawal',
	'y@bth': 'Yotvata',
	'b&yr': 'Bahir',
	'byt': 'Beit',
	'xyrwt': 'Herut',
	's@ymcqy': 'Steimatzky',
	'p^wwr': 'Power',
	'sn@r': 'Center',
	'$w^rmh': 'Schawarma',
	'dlyh': 'Dalia',
	'$ypwdy': 'Shipudei',
	'smy': 'Sami',
	'bkkr': 'Bakikar',
	'^yys': 'Ace',
	'gn': 'Gan',
	'$mw^l': 'Shmuel',
	'zr pwr yw': 'Zer 4 U',
}

def hebrew_transliterate(s):
	newstr = reverse(string.translate(s, heb2eng_table))
	i = 0
	while i < 2:
		for key in hebrew_words.keys():
			newstr = string.replace(newstr, key, hebrew_words[key])
		i = i + 1
	return newstr

def qifdata2str(data, year):
	s = ''
	for key in data.keys():
		s = s + key + data[key]
		if key == 'D':
			s = s + '/' + year
		s = s + '\n'
	return s

class CSV2QIF_Base:
	def __init__(self, year):
		self.year = year

	def run(self):
		reader = csv.reader(file(self.basename() + '.csv'))
		writer = file(self.basename() + '.qif', 'w')
		writer.write(self.qif_header())
		writer.write('\n')
		for row in reader:
			qifdata = self.row2qif(cleanrow(row))
			if not qifdata: continue
			writer.write(qifdata2str(qifdata, self.year))
			writer.write('^\n')
		writer.close()

class Tnuot(CSV2QIF_Base):
	def basename(self):
		return 'Tnuot'
	
	def qif_header(self):
		return '!Type:Bank'

	def row2qif(self, row):
		if not len(row[3]): return None
		if not row[1] and not row[2]: return None
		if not row[4]: return None
		trans = hebrew_transliterate(row[5])
		if len(trans) == 0: return None
		return {'D': row[3], 'T': creditdebit(row[1], row[2]), 'P': trans, 'N': row[4]}
	
class CC(CSV2QIF_Base):
	def basename(self):
		return 'CC'
	def qif_header(self):
		return '!Type:CCard'
	
	def row2qif(self, row):
		if row[1] == '0.00': return None # Skip total line
		trans = hebrew_transliterate(row[4])
		if len(trans) == 0: return None
		return {'D': row[5], 'T': '-' + row[2], 'P': trans, 'N': row[3]}
		
if __name__ == '__main__':
	year = '2004'
	classes = {'Tnuot': Tnuot, 'CC': CC}
	import sys
	for type in sys.argv[1:]:
		print 'Handling class %s' % (type,)
		c = classes[type]
		instance = c(year)
		instance.run()
		print 'Done'

