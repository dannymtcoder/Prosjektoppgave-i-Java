ASP = java -jar $(ASP_JAR)
ASP_JAR = /site/ifivar/opt/ifi/inf2100/asp.jar
ASP_EXPR = $(ASP) -testexpr

all:	easter.out expressions.out gcd.out heron.out mini.out primes.out

easter.out:	easter.asp
	$(ASP) easter.asp >easter.out

expressions.out:	expressions.asp
	$(ASP_EXPR) expressions.asp
	mv expressions.log expressions.out

gcd.out:	gcd.asp gcd.in
	$(ASP) gcd.asp <gcd.in >gcd.out

heron.out:	heron.asp heron.in
	$(ASP) heron.asp <heron.in >heron.out

mini.out:	mini.asp
	$(ASP) mini.asp >mini.out

primes.out:	primes.asp
	$(ASP) primes.asp >primes.out

clean:		
	rm -f *.log *.out *~
