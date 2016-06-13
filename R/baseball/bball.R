# http://www.baseball-almanac.com/stats.shtml
bball.data <- read.csv("bball-201308.csv", stringsAsFactors=FALSE)
bball.data$X <- NULL
bball.data$X.1 <- NULL
bball.data$X.2 <- NULL
bball.data$X.3 <- NULL
bball.data$X.4 <- NULL
bball.data$Avg <- NULL
bball.data$Obp. <- NULL
bball.data$PA <- NULL
bball.data$Slg <- NULL
bball.data$SB. <- NULL
bball.data$Team <- factor(bball.data$Team)
rownames(bball.data) <- bball.data$Player
bball.data$Player <- NULL
#Avg or AB = Number of Hits (divided by) Number of At Bats
bball.data$AVG <- bball.data$H / bball.data$AB

# On Base Percentage = (Hits + Walks + Hit-By-Pitch) divided by (At Bats + Walks+ Hit-By-Pitch + Sac Flys)
bball.data$OBP <- (bball.data$H + bball.data$TBB + bball.data$HBP) / (bball.data$AB + bball.data$TBB + bball.data$HBP + bball.data$SF)

# Plate Appearances = At-Bats + Bases on Balls + Hit By Pitcher + Sacrifice Hits + Sacrifice Flies + Times Reached on Defensive Interference
# factor used to determine the yearly batting champion. Currently, 3.1 plate appearances per game are required for batting title eligibility.
bball.data$PA <- (bball.data$AB + bball.data$TBB + bball.data$HBP + bball.data$SH.B + bball.data$SF + bball.data$Int)

#Total Bases = Number of (Singles + [2 x Doubles] +[ 3 x Triples] + [4 x Home Runs])
#worth as a batter
bball.data$TB <- bball.data$X1B + (2*bball.data$X2B) + (3*bball.data$X3B) + (4*bball.data$HR)

# Runs Created = On Base Percentage x Total Bases
# Total offensive production
bball.data$RC <- bball.data$OBP * bball.data$TB
#Slugging Average or Total Bases per At-bat = Number of (Singles + [2 x Doubles] +[ 3 x Triples] + [4 x Home Runs]) divided by At Bats
bball.data$SLG <- ((bball.data$X1B + (2*bball.data$X2B) + (3*bball.data$X3B) + (4*bball.data$HR)) / bball.data$AB)

#Stolen Base Attempts
bball.data$SBA <- bball.data$SB + bball.data$CS

# Stolen Base Percentage = Number of Successful Stolen Bases (divided by) Number of Stolen Base Attempts
bball.data$SBP <- bball.data$SB / bball.data$SBA

# Equipo Cuba
cuba.data <- bball.data[bball.data$Team=="Cuba",]
liga_mean_avg = mean(bball.data$AVG, na.rm = TRUE)
liga_median_avg = median(bball.data$AVG, na.rm = TRUE)
liga_sd_avg = sd(bball.data$AVG, na.rm = TRUE)
cuba_mean_avg = mean(cuba.data$AVG, na.rm = TRUE)
cuba_median_avg = median(cuba.data$AVG, na.rm = TRUE)
cuba_sd_avg = sd(cuba.data$AVG, na.rm = TRUE)
#Order by decreasing avg
cuba.data[order(cuba.data$AVG, na.last=TRUE, decreasing=TRUE),]
