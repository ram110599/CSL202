:-style_check(-discontiguous).
os_name(200,ubuntu).
os_version(200,16.04).
os_arch(200,64).
os_max_open_files(200,2000).
os_max_connections(200,1000).
os_provides_libs(200,[lxml, gcc, foo, bar]).
os_name(201,fedore).
os_version(201,23).
os_arch(201,32).
os_max_open_files(201,1500).
os_max_connections(201,700).
os_provides_libs(201,[lib_a, lib_b, lib_image, bar]).
machine_type(120,physical).
machine_os(120,200	).
machine_ram(120,16384).
machine_disk(120,6144).
machine_cpu(120,16).
machine_type(121,virtual).
machine_os(121,201).
machine_ram(121,4096).
machine_disk(121,256).
machine_cpu(121,2).
softwareapp_name(300,mysql_server).
softwareapp_min_ram(300,512).
softwareapp_min_disk(300,4).
softwareapp_min_cpu(300,2).
softwareapp_os(300,[200, 201]).
softwareapp_libs(300,[lxml, gcc, foo, bar]).
softwareapp_name(301,apache_web_server).
softwareapp_min_ram(301,512).
softwareapp_min_disk(301,1).
softwareapp_min_cpu(301,2).
softwareapp_os(301,[200]).
softwareapp_libs(301,[lib_a, lib_a, gcc, lib_b, bar]).
softwareapp_name(302,imageprocessing_server).
softwareapp_min_ram(302,2).
softwareapp_min_disk(302,100).
softwareapp_min_cpu(302,8).
softwareapp_os(302,[200]).
softwareapp_libs(302,[keras, gcc, lib_image, bar]).
ram_check(ID1,ID2) :- softwareapp_min_ram(ID1,X), machine_ram(ID2,Y),X=<Y.
disk_check(ID1,ID2) :- softwareapp_min_disk(ID1,X), machine_disk(ID2,Y), X=<Y.
cpu_check(ID1,ID2) :- softwareapp_min_cpu(ID1,X), machine_cpu(ID2,Y), X=<Y.
all_from_first_in_second(List1, List2) :- forall(member(Element,List1), member(Element,List2)).
check_lib(ID1,ID2) :- softwareapp_libs(ID1,X),machine_os(ID2,Y),os_provides_libs(Y,Z),all_from_first_in_second(X,Z).
check_os(ID1,ID2) :- softwareapp_os(ID1,X), machine_os(ID2,Y), member(Y,X).
check(ID1,ID2) :- ram_check(ID1,ID2), disk_check(ID1,ID2), cpu_check(ID1,ID2), check_os(ID1,ID2), check_lib(ID1,ID2).
check1(ID1,ID2) :-findall(ID2,check(ID1,ID2),Z),writeln(Z).
check2(ID1) :- findall(ID2,check(ID1,ID2),Z),writeln(Z).
