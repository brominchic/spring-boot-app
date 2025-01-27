INSERT INTO public.roles(
	id, name)
	VALUES (1,'user' );
INSERT INTO public.users(
	id, role, login, password)
	VALUES (1, 1, 'brom', '$2a$05$w2M8LA8Hl6Bi20yshxjAguh8FFg2aDMKv0Kj71YwEha1Xj9c2gaUC');