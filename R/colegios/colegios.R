# colegios PSU 2013, RM, Santiago de Chile
cole.data <- read.csv("colegios.csv", stringsAsFactors=FALSE)
rownames(cole.data) <- cole.data$colegio
cole.data$nombre <- NULL
santiago.data <- cole.data[cole.data$provincia=="SANTIAGO",]
partic.data <- santiago.data[santiago.data$dependencia=="PARTICULAR PAGADO",]
media = mean(partic.data$psu)
desviacion = sd(partic.data$psu)

cole.within.sd.data<-partic.data[partic.data$psu>(media-desviacion) & partic.data$psu<(media+desviacion), ]
cole.within.sd.data<-partic.data[partic.data$psu>(media-(desviacion/2) & partic.data$psu<(media+desviacion), ]
