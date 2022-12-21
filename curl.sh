
EXAMPLE="example"
NEEDARG=""
NEEDRUN=""
ARGS=""
ASH="./a.sh"
RUNALREADY=""
set_args () {
 local a=$2
 local b=$3
 local c=$4
 eval "$NEEDARG=$1"
 ARGS="$a $b $c"
 NEEDARG=""
}
give () {
 if [ $NEEDARG ]
 then
  set_args $1 $ARGS
 else
  echo "no need to give anything"
 fi
}
next () {
 if [ $NEEDRUN ]
 then
  echo "run it first"
 else
  if [ $NEEDARG ]
  then
   echo "give an argument first ($NEEDARG)"
  else
   $EXAMPLE $ARGS > $ASH
   cat $ASH
   RUNALREADY=""
  fi
 fi
}
run () {
 if [ $RUNALREADY ]
 then
  echo "already run"
 else
  if [ $NEEDRUN ]
  then
   . $ASH
   NEEDRUN=""
   RUNALREADY="yes"
  else
   echo "this does not need to be run"
  fi
 fi
}


example () {
 printf "%-26s%s\n" "example1" "login"
 printf "%-26s%s\n" "example2a session" "get and create planet"
 printf "%-26s%s\n" "example2b session id" "delete planet"
 printf "%-26s%s\n" "example3a session" "get and create moon"
 printf "%-26s%s\n" "example3b session id" "delete moon"
 printf "%-26s%s\n" "example4a session" "create planet"
 printf "%-26s%s\n" "example4b session id" "create moon for planet"
 printf "%-26s%s\n" "example4c session id1 id2" "delete planet and check on  dangling moon"
 EXAMPLE="example1"
}

example1 () {
 printf "#login:\n\n\n"
 echo "post '/login' '{\"username\": \"person\", \"password\": \"password\"}'"
 EXAMPLE="example2a"
 NEEDARG='a'
 NEEDRUN="yes"
}

example2a () {
 printf "#planets test create:\n\n\n"
 echo "get '/api/planets' '$1'"
 echo "post '/api/planet' '$1' '"'{"name":"planetname","ownerId":1}'"'"
 echo "get '/api/planets' '$1'"
 echo "get '/api/planet/id/1' '$1'"
 echo "get '/api/planet/id/2' '$1'"
 echo "get '/api/planet/id/3' '$1'"
 echo "get '/api/planet/planetname' '$1'"
 EXAMPLE="example2b"
 NEEDARG='b'
 NEEDRUN="yes"
}
example2b () {
 printf "#planets test delete:\n\n\n"
 echo "delete '/api/planet/$2' '$1'"
 echo "get '/api/planets' '$1'"
 echo "get '/api/planet/planetname' '$1'"
 EXAMPLE="example3a"
 NEEDRUN="yes"
}
example3a () {
 printf "#moons test create:\n\n\n"
 echo "get '/api/moons' '$1'"
 echo "post '/api/moon' '$1' '"'{"name":"moonname","myPlanetId":1}'"'"
 echo "get '/api/moons' '$1'"
 echo "get '/api/planet/1/moons' '$1'"
 echo "get '/api/moon/id/1' '$1'"
 echo "get '/api/moon/id/2' '$1'"
 echo "get '/api/moon/id/3' '$1'"
 echo "get '/api/moon/moonname' '$1'"
 EXAMPLE="example3b"
 NEEDARG='b'
 NEEDRUN="yes"
}
example3b () {
 printf "#planets test delete:\n\n\n"
 echo "delete '/api/moon/$2' '$1'"
 echo "get '/api/moons' '$1'"
 echo "get '/api/planet/1/moons' '$1'"
 echo "get '/api/moon/planetname' '$1'"
 EXAMPLE="example4a"
 NEEDRUN="yes"
}
example4a () {
 printf "#test dangling moons... planet creation:\n\n\n"
 echo "post '/api/planet' '$1' '"'{"name":"planetname","ownerId":1}'"'"
 EXAMPLE="example4b"
 NEEDARG='b'
 NEEDRUN="yes"
}
example4b () {
 printf "#test dangling moons... moon creation:\n\n\n"
 echo "post '/api/moon' '$1' '"'{"name":"moonname","myPlanetId":'"$2}'"
 EXAMPLE="example4c"
 NEEDARG='c'
 NEEDRUN="yes"
}
example4c () {
 printf "#test dangling moons... planet deletion:\n\n\n"
 echo "get '/api/planet/$2/moons' '$1'"
 echo "get '/api/moons' '$1'"
 echo "get '/api/moon/id/$3' '$1'"
 echo "delete '/api/planet/$2' '$1'"
 echo "get '/api/planet/$2/moons' '$1'"
 echo "get '/api/moons' '$1'"
 echo "get '/api/moon/id/$3' '$1'"
 EXAMPLE="imdone"
 NEEDRUN="yes"
}
imdone () {
 echo "presentation over"
}



CURL="curl -s -i -o -"
post () {
 if [ $# -eq 1 ]
 then
  printf "\n\npost $1\n"
  $CURL -X POST "http://localhost:7000$1"
 fi
 if [ $# -eq 2 ]
 then
  printf "\n\npost $1 \n $2\n"
  $CURL -X POST -H "content-type: application/json" -d "$2" "http://localhost:7000$1"
 fi
 if [ $# -eq 3 ]
 then
  printf "\n\npost $1 \n $2 \n $3\n"
  $CURL -X POST -H "Cookie: JSESSIONID=$2" -H "content-type: application/json" -d "$3" "http://localhost:7000$1"
 fi
}
get () {
 printf "\n\nget $1 \n $2\n"
 $CURL -X GET -H "Cookie: JSESSIONID=$2" "http://localhost:7000$1"
}
delete () {
 printf "\n\ndelete $1 \n $2\n"
 $CURL -X DELETE -H "Cookie: JSESSIONID=$2" "http://localhost:7000$1"
}
