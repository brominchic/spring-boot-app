INSERT INTO public.roles(
	id, name)
	VALUES (1,'user' );
INSERT INTO public.users(
	id, role, login, password)
	VALUES (1, 1, 'brom', '$2a$05$09svtM/3ECV4/UtldP72quZOp1OUVCzYUvAcWz549QNobia1GvvXa');