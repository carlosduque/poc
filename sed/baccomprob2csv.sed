/CREDOMATIC/d
/Empleado/d
/0801197809521/d
/Departamento/d
/CONCEPTOS/d
/TOTALES/d
/El Valor del Mes/d
s/,//g
s/H528 //g
s/Salario Lempiras 15\.00/Salario,/g
s/[[:cntrl:]]//g
/^$/d
s/^[ ]*//
s/[\t ][\t ]*/ /g
s/^\([0-9][0-9]\/\)/\n\1/g
s/\([0-9][0-9]*\.[0-9][0-9]*\)/,\1/g
#/^[A-Z]/ {
        #s/^DIC/200712/
	#s/,\([0-9]*\),/\1,/g
	#s/,\([A-Z]*\),/\1,/g
	#s/ //g
        #s:^\(.*\)/\(.*\)/\(.*\):\2 \1 \3:
#}
