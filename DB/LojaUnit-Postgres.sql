PGDMP         	            	    x            lojaunit    12.4    12.4 K    l           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            m           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            n           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            o           1262    16393    lojaunit    DATABASE     �   CREATE DATABASE lojaunit WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Portuguese_Brazil.1252' LC_CTYPE = 'Portuguese_Brazil.1252';
    DROP DATABASE lojaunit;
                postgres    false            �            1259    16433 	   categoria    TABLE     x   CREATE TABLE public.categoria (
    id integer NOT NULL,
    nome character varying(200),
    ativo boolean NOT NULL
);
    DROP TABLE public.categoria;
       public         heap    postgres    false            p           0    0    COLUMN categoria.ativo    COMMENT     C   COMMENT ON COLUMN public.categoria.ativo IS '0- inativo
1- ativo';
          public          postgres    false    207            �            1259    16431    categoria_id_seq    SEQUENCE     �   CREATE SEQUENCE public.categoria_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.categoria_id_seq;
       public          postgres    false    207            q           0    0    categoria_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.categoria_id_seq OWNED BY public.categoria.id;
          public          postgres    false    206            �            1259    16409    clientes    TABLE     a  CREATE TABLE public.clientes (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    cpf character varying(11) NOT NULL,
    email character varying(200) NOT NULL,
    data_nascimento date,
    sexo character varying(20),
    nome_social character varying(100),
    apelido character varying(100),
    telefone character varying(13)
);
    DROP TABLE public.clientes;
       public         heap    postgres    false            �            1259    16407    clientes_id_seq    SEQUENCE     �   CREATE SEQUENCE public.clientes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.clientes_id_seq;
       public          postgres    false    203            r           0    0    clientes_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.clientes_id_seq OWNED BY public.clientes.id;
          public          postgres    false    202            �            1259    16498    faq    TABLE     �   CREATE TABLE public.faq (
    id integer NOT NULL,
    datahora time with time zone NOT NULL,
    texto text NOT NULL,
    id_produto integer NOT NULL
);
    DROP TABLE public.faq;
       public         heap    postgres    false            �            1259    16496 
   faq_id_seq    SEQUENCE     �   CREATE SEQUENCE public.faq_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.faq_id_seq;
       public          postgres    false    215            s           0    0 
   faq_id_seq    SEQUENCE OWNED BY     9   ALTER SEQUENCE public.faq_id_seq OWNED BY public.faq.id;
          public          postgres    false    214            �            1259    16470    forma_pagamento    TABLE     �   CREATE TABLE public.forma_pagamento (
    id integer NOT NULL,
    forma character varying(200) NOT NULL,
    descricao text,
    ativo boolean DEFAULT true NOT NULL
);
 #   DROP TABLE public.forma_pagamento;
       public         heap    postgres    false            t           0    0    COLUMN forma_pagamento.ativo    COMMENT     I   COMMENT ON COLUMN public.forma_pagamento.ativo IS '0- inativo
1- ativo';
          public          postgres    false    211            �            1259    16468    forma_pagamento_id_seq    SEQUENCE     �   CREATE SEQUENCE public.forma_pagamento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.forma_pagamento_id_seq;
       public          postgres    false    211            u           0    0    forma_pagamento_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.forma_pagamento_id_seq OWNED BY public.forma_pagamento.id;
          public          postgres    false    210            �            1259    16446 
   fornecedor    TABLE     �   CREATE TABLE public.fornecedor (
    id integer NOT NULL,
    nome character varying(200) NOT NULL,
    endereco text,
    telefone character varying(20),
    cnpj character varying(20) NOT NULL,
    email character varying(200)
);
    DROP TABLE public.fornecedor;
       public         heap    postgres    false            �            1259    16444    fornecedor_id_seq    SEQUENCE     �   CREATE SEQUENCE public.fornecedor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.fornecedor_id_seq;
       public          postgres    false    209            v           0    0    fornecedor_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.fornecedor_id_seq OWNED BY public.fornecedor.id;
          public          postgres    false    208            �            1259    16531    itens_venda    TABLE     �   CREATE TABLE public.itens_venda (
    id_venda integer NOT NULL,
    id_produto integer NOT NULL,
    quantidade integer NOT NULL,
    valor_unitario double precision NOT NULL
);
    DROP TABLE public.itens_venda;
       public         heap    postgres    false            �            1259    16482    marcas    TABLE     v   CREATE TABLE public.marcas (
    id integer NOT NULL,
    nome character varying(200) NOT NULL,
    descricao text
);
    DROP TABLE public.marcas;
       public         heap    postgres    false            �            1259    16480    marcas_id_seq    SEQUENCE     �   CREATE SEQUENCE public.marcas_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.marcas_id_seq;
       public          postgres    false    213            w           0    0    marcas_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.marcas_id_seq OWNED BY public.marcas.id;
          public          postgres    false    212            �            1259    16422    produto    TABLE     
  CREATE TABLE public.produto (
    id integer NOT NULL,
    nome character varying(200) NOT NULL,
    descricao text,
    preco_unitario double precision,
    unidade character varying(20),
    id_categoria integer,
    id_fornecedor integer,
    id_marca integer
);
    DROP TABLE public.produto;
       public         heap    postgres    false            �            1259    16420    produto_id_seq    SEQUENCE     �   CREATE SEQUENCE public.produto_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.produto_id_seq;
       public          postgres    false    205            x           0    0    produto_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.produto_id_seq OWNED BY public.produto.id;
          public          postgres    false    204            �            1259    16514    vendas    TABLE     �   CREATE TABLE public.vendas (
    id integer NOT NULL,
    datahora time with time zone DEFAULT now() NOT NULL,
    id_cliente integer NOT NULL,
    id_forma_pagamento integer NOT NULL,
    valor_total double precision
);
    DROP TABLE public.vendas;
       public         heap    postgres    false            �            1259    16512    vendas_id_seq    SEQUENCE     �   CREATE SEQUENCE public.vendas_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.vendas_id_seq;
       public          postgres    false    217            y           0    0    vendas_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.vendas_id_seq OWNED BY public.vendas.id;
          public          postgres    false    216            �
           2604    16436    categoria id    DEFAULT     l   ALTER TABLE ONLY public.categoria ALTER COLUMN id SET DEFAULT nextval('public.categoria_id_seq'::regclass);
 ;   ALTER TABLE public.categoria ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    207    206    207            �
           2604    16412    clientes id    DEFAULT     j   ALTER TABLE ONLY public.clientes ALTER COLUMN id SET DEFAULT nextval('public.clientes_id_seq'::regclass);
 :   ALTER TABLE public.clientes ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    203    202    203            �
           2604    16501    faq id    DEFAULT     `   ALTER TABLE ONLY public.faq ALTER COLUMN id SET DEFAULT nextval('public.faq_id_seq'::regclass);
 5   ALTER TABLE public.faq ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    214    215    215            �
           2604    16473    forma_pagamento id    DEFAULT     x   ALTER TABLE ONLY public.forma_pagamento ALTER COLUMN id SET DEFAULT nextval('public.forma_pagamento_id_seq'::regclass);
 A   ALTER TABLE public.forma_pagamento ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    211    210    211            �
           2604    16449    fornecedor id    DEFAULT     n   ALTER TABLE ONLY public.fornecedor ALTER COLUMN id SET DEFAULT nextval('public.fornecedor_id_seq'::regclass);
 <   ALTER TABLE public.fornecedor ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    208    209    209            �
           2604    16485 	   marcas id    DEFAULT     f   ALTER TABLE ONLY public.marcas ALTER COLUMN id SET DEFAULT nextval('public.marcas_id_seq'::regclass);
 8   ALTER TABLE public.marcas ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    213    212    213            �
           2604    16425 
   produto id    DEFAULT     h   ALTER TABLE ONLY public.produto ALTER COLUMN id SET DEFAULT nextval('public.produto_id_seq'::regclass);
 9   ALTER TABLE public.produto ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    204    205    205            �
           2604    16517 	   vendas id    DEFAULT     f   ALTER TABLE ONLY public.vendas ALTER COLUMN id SET DEFAULT nextval('public.vendas_id_seq'::regclass);
 8   ALTER TABLE public.vendas ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    217    217            ^          0    16433 	   categoria 
   TABLE DATA           4   COPY public.categoria (id, nome, ativo) FROM stdin;
    public          postgres    false    207   U       Z          0    16409    clientes 
   TABLE DATA           o   COPY public.clientes (id, nome, cpf, email, data_nascimento, sexo, nome_social, apelido, telefone) FROM stdin;
    public          postgres    false    203   %U       f          0    16498    faq 
   TABLE DATA           >   COPY public.faq (id, datahora, texto, id_produto) FROM stdin;
    public          postgres    false    215   aU       b          0    16470    forma_pagamento 
   TABLE DATA           F   COPY public.forma_pagamento (id, forma, descricao, ativo) FROM stdin;
    public          postgres    false    211   ~U       `          0    16446 
   fornecedor 
   TABLE DATA           O   COPY public.fornecedor (id, nome, endereco, telefone, cnpj, email) FROM stdin;
    public          postgres    false    209   �U       i          0    16531    itens_venda 
   TABLE DATA           W   COPY public.itens_venda (id_venda, id_produto, quantidade, valor_unitario) FROM stdin;
    public          postgres    false    218   �U       d          0    16482    marcas 
   TABLE DATA           5   COPY public.marcas (id, nome, descricao) FROM stdin;
    public          postgres    false    213   �U       \          0    16422    produto 
   TABLE DATA           v   COPY public.produto (id, nome, descricao, preco_unitario, unidade, id_categoria, id_fornecedor, id_marca) FROM stdin;
    public          postgres    false    205   �U       h          0    16514    vendas 
   TABLE DATA           [   COPY public.vendas (id, datahora, id_cliente, id_forma_pagamento, valor_total) FROM stdin;
    public          postgres    false    217   V       z           0    0    categoria_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.categoria_id_seq', 1, false);
          public          postgres    false    206            {           0    0    clientes_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.clientes_id_seq', 1, true);
          public          postgres    false    202            |           0    0 
   faq_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.faq_id_seq', 1, false);
          public          postgres    false    214            }           0    0    forma_pagamento_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.forma_pagamento_id_seq', 1, false);
          public          postgres    false    210            ~           0    0    fornecedor_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.fornecedor_id_seq', 1, false);
          public          postgres    false    208                       0    0    marcas_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.marcas_id_seq', 1, false);
          public          postgres    false    212            �           0    0    produto_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.produto_id_seq', 1, false);
          public          postgres    false    204            �           0    0    vendas_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.vendas_id_seq', 1, false);
          public          postgres    false    216            �
           2606    16438    categoria categoria_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.categoria DROP CONSTRAINT categoria_pkey;
       public            postgres    false    207            �
           2606    16417    clientes clientes_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.clientes DROP CONSTRAINT clientes_pkey;
       public            postgres    false    203            �
           2606    16456    fornecedor cnpj_unique 
   CONSTRAINT     Q   ALTER TABLE ONLY public.fornecedor
    ADD CONSTRAINT cnpj_unique UNIQUE (cnpj);
 @   ALTER TABLE ONLY public.fornecedor DROP CONSTRAINT cnpj_unique;
       public            postgres    false    209            �
           2606    16419    clientes cpf_unique 
   CONSTRAINT     M   ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT cpf_unique UNIQUE (cpf);
 =   ALTER TABLE ONLY public.clientes DROP CONSTRAINT cpf_unique;
       public            postgres    false    203            �
           2606    16506    faq faq_pkey 
   CONSTRAINT     J   ALTER TABLE ONLY public.faq
    ADD CONSTRAINT faq_pkey PRIMARY KEY (id);
 6   ALTER TABLE ONLY public.faq DROP CONSTRAINT faq_pkey;
       public            postgres    false    215            �
           2606    16479 $   forma_pagamento forma_pagamento_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.forma_pagamento DROP CONSTRAINT forma_pagamento_pkey;
       public            postgres    false    211            �
           2606    16454    fornecedor fornecedor_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.fornecedor
    ADD CONSTRAINT fornecedor_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.fornecedor DROP CONSTRAINT fornecedor_pkey;
       public            postgres    false    209            �
           2606    16535    itens_venda itens_venda_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.itens_venda
    ADD CONSTRAINT itens_venda_pkey PRIMARY KEY (id_venda, id_produto);
 F   ALTER TABLE ONLY public.itens_venda DROP CONSTRAINT itens_venda_pkey;
       public            postgres    false    218    218            �
           2606    16490    marcas marcas_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.marcas
    ADD CONSTRAINT marcas_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.marcas DROP CONSTRAINT marcas_pkey;
       public            postgres    false    213            �
           2606    16430    produto produto_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.produto DROP CONSTRAINT produto_pkey;
       public            postgres    false    205            �
           2606    16519    vendas vendas_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT vendas_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.vendas DROP CONSTRAINT vendas_pkey;
       public            postgres    false    217            �
           2606    16439    produto fk_categoria_produto    FK CONSTRAINT     �   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT fk_categoria_produto FOREIGN KEY (id_categoria) REFERENCES public.categoria(id) ON UPDATE CASCADE ON DELETE CASCADE;
 F   ALTER TABLE ONLY public.produto DROP CONSTRAINT fk_categoria_produto;
       public          postgres    false    2756    207    205            �
           2606    16525    vendas fk_cliente_venda    FK CONSTRAINT     �   ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT fk_cliente_venda FOREIGN KEY (id_cliente) REFERENCES public.clientes(id) ON UPDATE CASCADE ON DELETE CASCADE;
 A   ALTER TABLE ONLY public.vendas DROP CONSTRAINT fk_cliente_venda;
       public          postgres    false    203    217    2750            �
           2606    16520    vendas fk_forma_pagto_venda    FK CONSTRAINT     �   ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT fk_forma_pagto_venda FOREIGN KEY (id_forma_pagamento) REFERENCES public.forma_pagamento(id) ON UPDATE CASCADE ON DELETE CASCADE;
 E   ALTER TABLE ONLY public.vendas DROP CONSTRAINT fk_forma_pagto_venda;
       public          postgres    false    211    217    2762            �
           2606    16457    produto fk_fornecedor_produto    FK CONSTRAINT     �   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT fk_fornecedor_produto FOREIGN KEY (id_fornecedor) REFERENCES public.fornecedor(id) ON UPDATE CASCADE ON DELETE CASCADE;
 G   ALTER TABLE ONLY public.produto DROP CONSTRAINT fk_fornecedor_produto;
       public          postgres    false    2760    209    205            �
           2606    16491    produto fk_marca_produto    FK CONSTRAINT     �   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT fk_marca_produto FOREIGN KEY (id_marca) REFERENCES public.marcas(id) ON UPDATE CASCADE ON DELETE CASCADE;
 B   ALTER TABLE ONLY public.produto DROP CONSTRAINT fk_marca_produto;
       public          postgres    false    2764    205    213            �
           2606    16507    faq fk_produto_faq    FK CONSTRAINT     �   ALTER TABLE ONLY public.faq
    ADD CONSTRAINT fk_produto_faq FOREIGN KEY (id_produto) REFERENCES public.produto(id) ON UPDATE CASCADE ON DELETE CASCADE;
 <   ALTER TABLE ONLY public.faq DROP CONSTRAINT fk_produto_faq;
       public          postgres    false    215    205    2754            �
           2606    16541    itens_venda fk_produto_item    FK CONSTRAINT     �   ALTER TABLE ONLY public.itens_venda
    ADD CONSTRAINT fk_produto_item FOREIGN KEY (id_produto) REFERENCES public.produto(id) ON UPDATE CASCADE ON DELETE CASCADE;
 E   ALTER TABLE ONLY public.itens_venda DROP CONSTRAINT fk_produto_item;
       public          postgres    false    2754    205    218            �
           2606    16536    itens_venda fk_venda_item    FK CONSTRAINT     �   ALTER TABLE ONLY public.itens_venda
    ADD CONSTRAINT fk_venda_item FOREIGN KEY (id_venda) REFERENCES public.vendas(id) ON UPDATE CASCADE ON DELETE CASCADE;
 C   ALTER TABLE ONLY public.itens_venda DROP CONSTRAINT fk_venda_item;
       public          postgres    false    2768    217    218            ^      x������ � �      Z   ,   x�3�.(��K�4��A�b��Cnbf�^r~.g�2����� Î�      f      x������ � �      b      x������ � �      `      x������ � �      i      x������ � �      d      x������ � �      \      x������ � �      h      x������ � �     