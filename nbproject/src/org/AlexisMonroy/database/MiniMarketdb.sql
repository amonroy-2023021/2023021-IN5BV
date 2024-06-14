set global time_zone = '-6:00';


drop database if exists DBMinIMarket2;
create database DBMinIMarket2;


use DBMinIMarket2;


create table Clientes(
	codigoCliente int not null,
    NITcliente varchar(10) not null,
    nombreCliente varchar(50) not null,
    apellidoCliente varchar(50) not null,
    direccionCliente varchar(150) not null,
    telefonoCliente varchar(15) not null,
    correoCliente varchar(45) not null,
    primary key PK_codigoCliente(codigoCliente)
);


create table CargoEmpleado(
    codigoCargoEmpleado int not null,
    nombreCargo varchar(45) not null,
    descripcionCargo varchar(60) not null,
    primary key PK_codigoCargoEmpleado(codigoCargoEmpleado)
);


create table Empleados(
	codigoEmpleado int not null,
    nombreEmpleado varchar(50) not null,
    apellidoEmpleado varchar(50) not null,
    sueldo decimal(10,2) not null,
    direccionEmpleado varchar(150) not null,
    turno varchar(30) not null,
    CargoEmpleado_codigoCargoEmpleado int not null,
    primary key PK_codigoEmpleado(codigoEmpleado),
    constraint FK_Empleados_CargoEmpleado foreign key Empleados(CargoEmpleado_codigoCargoEmpleado)
		references CargoEmpleado(codigoCargoEmpleado)
);


create table Proveedores(
	codigoProveedor int not null,
    NITproveedor varchar(10) not null,
    nombreProveedor varchar(60) not null,
    apellidoProveedor varchar(60) not null,
    direccionProveedor varchar(150) not null,
    razonSocial varchar(60) not null,
    contactoProveedor varchar(100) not null,
    primary key PK_codigoProveedor(codigoProveedor)
);

create table TelefonoProveedor(
	codigoTelefonoProveedor int not null auto_increment,
    numeroPrincipal varchar(8) not null,
    numeroSecundario varchar(8),
    observaciones varchar(45),
    Proveedores_codigoProveedor int not null,
    primary key PK_codigoTelefonoProveedor(codigoTelefonoProveedor),
    constraint FK_telefonoProveedor_Proveedores foreign key telefonoProveedor(Proveedores_codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table EmailProveedor(
	codigoEmailProveedor int not null auto_increment,
    emailProveedor varchar(50) not null,
    descripcion varchar(100) not null,
    Proveedores_codigoProveedor int not null,
    primary key PK_codigoEmailProveedor(codigoEmailProveedor),
    constraint FK_EmailProveedor_Proveedores foreign key EmailProveedor(Proveedores_codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table Compras(
    numeroDocumento int not null,
    fechaDocumento date not null,
    descripcion varchar(60) not null,
    totalDocumento decimal(10,2),
    primary key PK_numeroDocumento(numeroDocumento)
);

create table TipoProducto(
    codigoTipoProducto int not null,
    descripcion varchar(45) not null,
    primary key PK_codigoTipoProducto(codigoTipoProducto)
);

create table Productos(
    codigoProducto varchar(15) not null,
    descripcionProducto varchar(45) not null,
    precioUnitario decimal(10,2),
    precioDocena decimal(10,2),
    precioMayor decimal(10,2),
    existencia int,
    TipoProducto_codigoTipoProducto int not null,
    Proveedores_codigoProveedor int not null,
    primary key PK_codigoProducto(codigoProducto),
    constraint FK_Productos_TipoProducto foreign key Productos(TipoProducto_codigoTipoProducto)
    references TipoProducto(codigoTipoProducto),
    constraint FK_Productos_Proveedores foreign key Productos(Proveedores_codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table DetalleCompra(
	codigoDetalleCompra int not null auto_increment,
    costoUnitario decimal(10,2) not null,
    cantidad int not null,
    Productos_codigoProducto varchar(15) not null,
    Compras_numeroDocumento int not null,
    primary key PK_codigoDetalleCompra(codigoDetalleCompra),
    constraint FK_DetalleCompra_Compras foreign key DetalleCompra(Compras_numeroDocumento)
		references Compras(numeroDocumento),
	constraint FK_DetalleCompra_Productos foreign key DetalleCompra(Productos_codigoProducto)
		references Productos(codigoProducto)
);

create table Factura(
	numeroFactura int not null auto_increment,
    estado varchar(50) not null,
    totalFactura decimal(10,2),
    fechaFactura varchar(45),
    Clientes_codigoCliente int not null,
    Empleados_codigoEmpleado int not null,
    primary key PK_numeroFactura(numeroFactura),
    constraint FK_Factura_Clientes foreign key Factura(Clientes_codigoCliente)
		references Clientes(codigoCliente),
    constraint FK_Factura_Empleados foreign key Factura(Empleados_codigoEmpleado)
		references Empleados(codigoEmpleado)
);

create table DetalleFactura(
	codigoDetalleFactura int,
    precioUnitario decimal(10,2),
    cantidad int not null,
    Factura_numeroFactura int not null,
    Productos_codigoProducto varchar(15) not null,
    primary key PK_codigoDetalleFactura(codigoDetalleFactura),
    constraint FK_DetalleFactura_Compras foreign key DetalleFactura(Factura_numeroFactura)
		references Factura(numeroFactura),
	constraint FK_DetalleFactura_Productos foreign key DetalleFactura(Productos_codigoProducto)
		references Productos(codigoProducto)
);


-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- Clientes -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

-- AGREGAR ---------------------------------------------------------------------------------------------------------------------------- 

Delimiter $$

	create procedure sp_AgregarClientes(
    in codigoCliente int,
    in NITcliente varchar(10),
    in nombreCliente varchar(50),
    in apellidoCliente varchar(50),
    in direccionCliente varchar(150),
    in telefonoCliente varchar(15),
    in correoCliente varchar(45))
		Begin
			Insert into Clientes(
                        codigoCliente,
			NITcliente,
			nombreCliente,
			apellidoCliente,
			direccionCliente,
			telefonoCliente,
			correoCliente) values (
                        codigoCliente,
			NITcliente,
			nombreCliente,
			apellidoCliente,
			direccionCliente,
			telefonoCliente,
			correoCliente);
		End $$
    
Delimiter ;                  

-- LISTAR ---------------------------------------------------------------------------------------------------------------------------

Delimiter $$

	create procedure sp_ListarClientes()
		Begin
			select
            C.codigoCliente,
			C.NITcliente,
			C.nombreCliente,
			C.apellidoCliente,
			C.direccionCliente,
			C.telefonoCliente,
			C.correoCliente
            from Clientes C;
        End $$
        
Delimiter ;

-- BUSCAR ------------------------------------------------------------------------------- 

Delimiter $$

	create procedure sp_BuscarClientes(in codCli int)
		Begin
			select
            C.codigoCliente,
			C.NITcliente,
			C.nombreCliente,
			C.apellidoCliente,
			C.direccionCliente,
			C.telefonoCliente,
			C.correoCliente
            from Clientes C
            where codigoCliente = codCli;
        End $$
        
Delimiter ;

-- ELIMINAR -------------------------------------------------------------------------------------------

Delimiter $$

	create procedure sp_EliminarClientes(in codCli int)
		Begin
			Delete from Clientes
				where codigoCliente = codCli;
        End $$
        
Delimiter ;

-- EDITAR --

Delimiter $$

	create procedure sp_EditarClientes(in codCli int, in nCliente varchar(10), in nomClientes varchar(50), in apCliente varchar(50), in direcCliente varchar(150),
 in telCliente varchar(15), in corrCliente varchar(45))
		Begin
			Update Clientes C
				set
                C.codigoCliente = codCli,
				C.NITcliente = nCliente,
				C.nombreCliente = nomClientes,
				C.apellidoCliente = apCliente,
				C.direccionCliente = direcCliente,
				C.telefonoCliente = telCliente,
				C.correoCliente = corrCliente
                
                where codigoCliente = codCli;
		End $$
        
Delimiter ;



-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -
-- -- Llamado de procedimientos almacenados -- --
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -

-- Agregar clientes --

call sp_AgregarClientes(1, '114006350', 'As', 'Mo', 'Zona 7', '23002626', 'example2@kinal.edu.gt');
call sp_AgregarClientes(2, '254849542', 'Lis', 'Cruz', 'Zona 14', '54861278', 'example1@kinal.edu.gt');

-- Listar clientes --

call sp_ListarClientes();

-- Buscar cliente --

call sp_BuscarClientes(1);

-- Eliminar cliente --

call sp_EliminarClientes(1);

   -- Comprobacin --

	  call sp_ListarClientes();
      
-- Editar cliente --

call sp_EditarClientes(2, '894654641', 'ASU', 'Guillermo', 'Zona 15', '55813654', 'example@kinal.edu.gt');

	-- Comprobacion --
    
	   call sp_ListarClientes();
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

       
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- Procedimientos almacenados -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- Proveedores -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

-- AGREGAR -- 

Delimiter $$

	create procedure sp_AgregarProveedores(
    codigoProveedor int,
    in NITproveedor varchar(10),
    in nombreProveedor varchar(60),
    in apellidoProveedor varchar(60),
    in direccionProveedor varchar(150),
    in razonSocial varchar(60),
    in contactoProveedor varchar(100))
		Begin
			Insert into Proveedores(
            codigoProveedor,
			NITproveedor,
			nombreProveedor,
			apellidoProveedor,
			direccionProveedor,
			razonSocial,
            contactoProveedor) values (
            codigoProveedor,
			NITproveedor,
			nombreProveedor,
			apellidoProveedor,
			direccionProveedor,
			razonSocial,
            contactoProveedor);
            
		End $$
    
Delimiter ;                  

-- LISTAR --

Delimiter $$

	create procedure sp_ListarProveedores()
		Begin
			select
            P.codigoProveedor,
            P.NITproveedor,
			P.nombreProveedor,
			P.apellidoProveedor,
			P.direccionProveedor,
			P.razonSocial,
            P.contactoProveedor
            from Proveedores P;
        End $$
        
Delimiter ;

-- BUSCAR --

Delimiter $$

	create procedure sp_BuscarProveedores(in codPro int)
		Begin
			select
            P.codigoProveedor,
            P.NITproveedor,
			P.nombreProveedor,
			P.apellidoProveedor,
			P.direccionProveedor,
			P.razonSocial,
            P.contactoProveedor
            from Proveedores P
            where codigoProveedor = codPro;
        End $$
        
Delimiter ;

-- ELIMINAR --

Delimiter $$

	create procedure sp_EliminarProveedores(in codPro int)
		Begin
			Delete from Proveedores
				where codigoProveedor = codPro;
        End $$
        
Delimiter ;

-- EDITAR --

Delimiter $$

	create procedure sp_EditarProveedores(in codPro int,in nPro varchar(10), in nomPro varchar(60), in apPro varchar(60),
			in direcPro varchar(150), in raSocial varchar(60), in conPrin varchar(100))
		Begin
			Update Proveedores P
				set
				P.NITproveedor = nPro,
				P.nombreProveedor = nomPro,
				P.apellidoProveedor = apPro,
				P.direccionProveedor = direcPro,
				P.razonSocial = raSocial,
				P.contactoProveedor = conPrin
                where codigoProveedor = codPro;
		End $$
        
Delimiter ;

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -
-- -- Llamado de procedimientos almacenados -- --
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -

-- Agregar proveedores --

call sp_AgregarProveedores(1, '114006350', 'Al', 'Causa', 'Zona 7', 'TechSolutions S.A. de C.V.', 'Cesar García');
call sp_AgregarProveedores(2, '949474654', 'Mo', 'asd', 'Zona 8', 'GlobalTech Solutions, Inc.', 'Tulio Ortiz');

-- Listar proveedores --

call sp_ListarProveedores();

-- Buscar proveedores --

call sp_BuscarProveedores(1);

-- Eliminar proveedor --

call sp_EliminarProveedores(1);

   -- Comprobacin --
   
	  call sp_ListarProveedores();

-- Editar proveedor --

call sp_EditarProveedores(2, '000000000', 'Alexis', 'was', 'Zona 10', 'Manimalista S.A.', 'Randy Rivas');

	-- Comprobacion --
    
	   call sp_ListarProveedores();
       
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

-- AGREGAR -- 

Delimiter $$

	create procedure sp_AgregarCompras(
    in numeroDocumento int,
    in fechaDocumento date,
    in descripcion varchar(60),
    in totalDocumento decimal(10,2))
		Begin
			Insert into Compras(
            numeroDocumento,
			fechaDocumento,
			descripcion,
			totalDocumento) values (
            numeroDocumento,
			fechaDocumento,
			descripcion,
			totalDocumento);
		End $$
    
Delimiter ;                  

-- LISTAR --

Delimiter $$

	create procedure sp_ListarCompras()
		Begin
			select
            C.numeroDocumento,
            C.fechaDocumento,
			C.descripcion,
			C.totalDocumento
            from Compras C;
        End $$
        
Delimiter ;

-- BUSCAR -- 

Delimiter $$
	create procedure sp_BuscarCompras(in numDoc int)
		Begin
			select
            C.numeroDocumento,
            C.fechaDocumento,
			C.descripcion,
			C.totalDocumento
            from Compras C
            where numeroDocumento = numDoc;
        End $$
Delimiter ;

-- ELIMINAR --

Delimiter $$
	create procedure sp_EliminarCompras(in numDoc int)
		Begin
			Delete from Compras
				where numeroDocumento = numDoc;
        End $$
Delimiter ;

-- EDITAR --

Delimiter $$
	create procedure sp_EditarCompras(in numDoc int,in feDoc date, in descripcion varchar(60),
			in toDoc decimal(10,2))
		Begin
			Update Compras C
				set
				 C.fechaDocumento = feDoc,
				 C.descripcion = descripcion,
				 C.totalDocumento = toDoc
                where numeroDocumento = numDoc;
		End $$
Delimiter ;



-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -
-- -- Llamado de procedimientos almacenados -- --
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -

-- Agregar compras --

call sp_AgregarCompras(1, '2002-10-01', 'Oso amoroso', 1500.00);
call sp_AgregarCompras(2, '2007-05-30', 'Espada de arthur', 13500.00);

-- Listar clientes --

call sp_ListarCompras();

-- Buscar cliente --

call sp_BuscarCompras(1);

-- Eliminar cliente --

call sp_EliminarCompras(1);

   -- Comprobacin --

	  call sp_ListarCompras();
      
-- Editar cliente --

call sp_EditarCompras(2, '2002-02-12', 'cd 2.0', 3000.00);

	-- Comprobacion --
    
	   call sp_ListarCompras();
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --




-- AGREGAR -- 

Delimiter $$

	create procedure sp_AgregarTipoProductos(
    in codigoTipoProducto int,
    in descripcion varchar(45))
		Begin
			Insert into TipoProducto(
                        codigoTipoProducto,
			descripcion) values (
                        codigoTipoProducto,
			descripcion);
		End $$
    
Delimiter ;                  

-- LISTAR --

Delimiter $$

	create procedure sp_ListarTipoProductos()
		Begin
			select
            TP.codigoTipoProducto,
            TP.descripcion
            from TipoProducto TP;
        End $$
        
Delimiter ;

-- BUSCAR -- 

Delimiter $$
	create procedure sp_BuscarTipoProductos(in codTP int)
		Begin
			select
            TP.codigoTipoProducto,
            TP.descripcion
            from TipoProducto TP
            where codigoTipoProducto = codTP;
        End $$
Delimiter ;

-- ELIMINAR --

Delimiter $$
	create procedure sp_EliminarTipoProductos(in codTP int)
		Begin
			Delete from TipoProducto
				where codigoTipoProducto = codTP;
        End $$
Delimiter ;

-- EDITAR --

Delimiter $$
	create procedure sp_EditarTipoProductos(in codTP int,in des varchar(45))
		Begin
			Update TipoProducto TP
				set
				 TP.descripcion = des
                where codigoTipoProducto = codTP;
		End $$
Delimiter ;




-- Agregar tipo de productos --

call sp_AgregarTipoProductos(1, 'Es de un tipo indestructible');
call sp_AgregarTipoProductos(2, 'Se considera peligroso');

-- Listar clientes --

call sp_ListarTipoProductos();

-- Buscar cliente --

call sp_BuscarTipoProductos(1);

-- Eliminar cliente --

call sp_EliminarTipoProductos(1);

   -- Comprobacin --

	  call sp_ListarTipoProductos();
      
-- Editar cliente --

call sp_EditarTipoProductos(2, 'Se considera de rango S');

	-- Comprobacion --
    
	   call sp_ListarTipoProductos();
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- - Cargo Empleados - -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
DELIMITER $$

CREATE PROCEDURE sp_AgregarCargoEmpleado(
    IN p_codigoCargoEmpleado INT,
    IN p_nombreCargo VARCHAR(45),
    IN p_descripcionCargo VARCHAR(60)
)
BEGIN
    INSERT INTO cargoEmpleado (codigoCargoEmpleado, nombreCargo, descripcionCargo)
    VALUES (p_codigoCargoEmpleado, p_nombreCargo, p_descripcionCargo);
END $$

DELIMITER ;
-- Listar cargo Empleados -----------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE sp_ListarCargoEmpleado()
BEGIN
    SELECT * FROM cargoEmpleado;
END $$

DELIMITER ;

-- Eliminar Cargo Empleados -------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE sp_EliminarCargoEmpleado(
    IN p_codigoCargoEmpleado INT
)
BEGIN
    DELETE FROM cargoEmpleado WHERE codigoCargoEmpleado = p_codigoCargoEmpleado;
END $$

DELIMITER ;

-- Editar Cargo Empleados --------------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE sp_EditarCargoEmpleado(
    IN p_codigoCargoEmpleado INT,
    IN p_nombreCargo VARCHAR(45),
    IN p_descripcionCargo VARCHAR(60)
)
BEGIN
    UPDATE cargoEmpleado
    SET nombreCargo = p_nombreCargo,
        descripcionCargo = p_descripcionCargo
    WHERE codigoCargoEmpleado = p_codigoCargoEmpleado;
END $$

DELIMITER ;

-- -- Agregar Cargo Empleado --------------------------------------------------------------------------------------------------

CALL sp_AgregarCargoEmpleado(1, 'Gerente', 'Responsable de la gestión general');
CALL sp_AgregarCargoEmpleado(2, 'Supervisor', 'Encargado de supervisar al personal');
CALL sp_AgregarCargoEmpleado(3, 'Asistente Administrativo', 'Brinda apoyo administrativo');

-- Eliminar Cargo Empleado --

CALL sp_EliminarCargoEmpleado(3);

-- Listar Cargo Empleados ---
CALL sp_ListarCargoEmpleado();

-- Editar Cargo Empleados ---
CALL sp_EditarCargoEmpleado(1, 'Director', 'Encargado de la dirección estratégica');

-- EMPLEADOS --
-- AGREGAR -- 

Delimiter $$

	create procedure sp_AgregarEmpleado(
    in codigoEmpleado int,
    in nombreEmpleado varchar(50),
    in apellidoEmpleado varchar(50),
    in sueldo decimal(10,2),
    in direccionEmpleado varchar(150),
    in turno varchar(30),
    in CargoEmpleado_codigoCargoEmpleado int)
		Begin
			Insert into Empleados(
            codigoEmpleado,
			nombreEmpleado,
		    apellidoEmpleado,
			sueldo,
			direccionEmpleado,
			turno,
			CargoEmpleado_codigoCargoEmpleado) values (
            codigoEmpleado,
			nombreEmpleado,
		    apellidoEmpleado,
			sueldo,
			direccionEmpleado,
			turno,
			CargoEmpleado_codigoCargoEmpleado);
		End $$
    
Delimiter ;                  

-- LISTAR --

Delimiter $$

	create procedure sp_ListarEmpleados()
		Begin
			select
            E.codigoEmpleado,
            E.nombreEmpleado,
		    E.apellidoEmpleado,
			E.sueldo,
			E.direccionEmpleado,
			E.turno,
			E.CargoEmpleado_codigoCargoEmpleado
            from Empleados E;
        End $$
        
Delimiter ;

-- BUSCAR -- 

Delimiter $$

	create procedure sp_BuscarEmpleado(in codEm int)
		Begin
			select
            E.codigoEmpleado,
            E.nombreEmpleado,
		    E.apellidoEmpleado,
			E.sueldo,
			E.direccionEmpleado,
			E.turno,
			E.CargoEmpleado_codigoCargoEmpleado
            from Empleados E
            where codigoEmpleado = codEm;
        End $$
        
Delimiter ;

-- ELIMINAR --

Delimiter $$

	create procedure sp_EliminarEmpleado(in codEm int)
		Begin
			Delete from Empleados
				where codigoEmpleado = codEm;
        End $$
        
Delimiter ;

-- EDITAR --

Delimiter $$

	create procedure sp_EditarEmpleado(in codEm int, in nomEm varchar(50), in apEm varchar(50), in sueldo decimal(10,2),
			in dircEm varchar(150), in turno varchar(15), in CE_codCE int)
		Begin
			Update Empleados E
				set
				E.nombreEmpleado = nomEm,
				E.apellidoEmpleado = apEm,
				E.sueldo = sueldo,
				E.direccionEmpleado = dircEm,
				E.turno = turno,
				E.CargoEmpleado_codigoCargoEmpleado = CE_codCE
                where codigoEmpleado = codEm;
		End $$
        
Delimiter ;

-- agregar Empleados --
CALL sp_AgregarEmpleado(1, 'Nombre del Empleado', 'Apellido del Empleado', 1000.00, 'Dirección del Empleado', 'Turno del Empleado', 1);
CALL sp_AgregarEmpleado(2, 'Nombre del Empleado', 'Apellido del Empleado', 1000.00, 'Dirección del Empleado', 'Turno del Empleado', 1);

-- Listar Empleados ---
CALL sp_ListarEmpleados();

-- Eliminar Empleados ---
CALL sp_EliminarEmpleado(2);

-- Editar Empleados ---
CALL sp_EditarEmpleado(1, 'Nuevo Nombre', 'Nuevo Apellido', 1500.00, 'Nueva Dirección', 'Nuevo Turno', 2);

-- ------------------------------Agregar productos-------------------------------------------------------------------
Delimiter $$
	create procedure sp_AgregarProductos(in codigoProducto varchar(15), descripcionProducto varchar(45), precioUnitario decimal(10,2), precioDocena decimal(10,2),
    precioMayor decimal(10,2), existencia int, tipoProducto_codigoTipoProducto int, proveedores_codigoProveedor int)
		Begin
			insert into Productos(codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, existencia, tipoProducto_codigoTipoProducto, proveedores_codigoProveedor)
            values (codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor,  existencia, tipoProducto_codigoTipoProducto, proveedores_codigoProveedor);
		End $$
Delimiter ;

CALL sp_AgregarProductos('PROD001','Producto de Ejemplo',10.50,100.00,500.00,50,2,2);

-- ------------------------------Listar productos-------------------------------------------------------------------
Delimiter $$
	create procedure sp_ListarProductos()
    Begin
		select
			PC.codigoProducto,
			PC.descripcionProducto,
            PC.precioUnitario,
            PC.precioDocena,
            PC.precioMayor,
            PC.existencia,
            PC.tipoProducto_codigoTipoProducto as tipoProducto,
            PC.proveedores_codigoProveedor as proveedor
        from Productos PC;
	End $$
Delimiter ;
call sp_ListarProductos();
-- ------------------------------Eliminar productos-------------------------------------------------------------------
Delimiter $$
	Create procedure sp_EliminarProductos(in codPC varchar(15))
		Begin
			Delete from Productos
            where codigoProducto = codPC;
		End $$
Delimiter ;

call sp_EliminarProductos(10);
-- ------------------------------Editar productos-------------------------------------------------------------------
Delimiter $$
	create procedure sp_EditarProductos(codPC varchar(15), desPC varchar(45), punitarioPC decimal (10,2), pdocenaPC decimal (10,2), pmayorPC decimal (10,2), exisPC int, tipoPC int, proveedoresPC int)
		Begin
			Update Productos PC
				set
				PC.descripcionProducto = desPC,
				PC.precioUnitario = punitarioPC,
				PC.precioDocena = pdocenaPC,
				PC.precioMayor = pmayorPC,
				PC.existencia = exisPC,
				PC.tipoProducto_codigoTipoProducto = tipoPC,
				PC.proveedores_codigoProveedor = proveedoresPC
            where codigoProducto = codPC;
		End $$
Delimiter ;

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- Procedimientos almacenados -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- Factura -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- ---
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

-- AGREGAR -- 

Delimiter $$

	create procedure sp_AgregarFactura(
    in numeroFactura int,
    in estado varchar(50),
    in fechaFactura varchar(45),
    in Clientes_codigoCliente int,
    in Empleados_codigoEmpleado int)
		Begin
			Insert into Factura(
            numeroFactura,
			estado,
			fechaFactura,
			Clientes_codigoCliente,
			Empleados_codigoEmpleado) values (
            numeroFactura,
			estado,
			fechaFactura,
			Clientes_codigoCliente,
			Empleados_codigoEmpleado);
		End $$
    
Delimiter ;
CALL sp_AgregarFactura(1, 'Pendiente', '2024-06-13', 2, 1);
                  
-- select * from Empleados;
-- LISTAR --

Delimiter $$

	create procedure sp_ListarFacturas()
		Begin
			select
            F.numeroFactura,
            F.estado,
			F.totalFactura,
			F.fechaFactura,
			F.Clientes_codigoCliente,
			F.Empleados_codigoEmpleado
            from Factura F;
        End $$
        
Delimiter ;

-- BUSCAR -- 

Delimiter $$

	create procedure sp_BuscarFactura(in codF int)
		Begin
			select
            F.numeroFactura,
            F.estado,
			F.totalFactura,
			F.fechaFactura,
			F.Clientes_codigoCliente,
			F.Empleados_codigoEmpleado
            from Factura F
            where numeroFactura = codF;
        End $$
        
Delimiter ;

-- ELIMINAR --

Delimiter $$

	create procedure sp_EliminarFactura(in codF int)
		Begin
			Delete from Factura
				where numeroFactura = codF;
        End $$
        
Delimiter ;

-- EDITAR --

Delimiter $$

	create procedure sp_EditarFactura(in codF int, in es varchar(50), in feF varchar(45),
			in C_codC int, in E_codE int)
		Begin
			Update Factura F
				set
				F.estado = es,
				F.fechaFactura = feF,
				F.Clientes_codigoCliente = C_codC,
				F.Empleados_codigoEmpleado = E_codE
                where numeroFactura = codF;
		End $$
        
Delimiter ;


-- ------------------------------Agregar detalle factura-------------------------------------------------------------------
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- Procedimientos almacenados -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- - Detalle factura -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

-- AGREGAR -- 

Delimiter $$

	create procedure sp_AgregarDetalleFactura(
    in codigoDetalleFactura int,
    in cantidad int,
    in Factura_numeroFactura int,
    in Productos_codigoProducto varchar(15))
		Begin
			Insert into DetalleFactura(
            codigoDetalleFactura,
			cantidad ,
            Factura_numeroFactura,
			Productos_codigoProducto) values (
            codigoDetalleFactura,
			cantidad ,
            Factura_numeroFactura,
			Productos_codigoProducto);
		End $$
    select * from Factura;
Delimiter ;                  
CALL sp_AgregarDetalleFactura(1, 100.00, 1, 'PROD001');
-- ------------------------------Listar detalle factura-------------------------------------------------------------------
Delimiter $$
	create procedure sp_ListarDetalleFactura()
    Begin
		select
			DF.codigoDetalleFactura,
			DF.precioUnitario,
            DF.cantidad,
            DF.factura_numeroFactura as numeroFactura,
            DF.productos_codigoProducto as codigoProducto
        from DetalleFactura DF;
	End $$
Delimiter ;
call sp_ListarDetalleFactura();
-- ------------------------------Eliminar detalle factura-------------------------------------------------------------------
Delimiter $$
	Create procedure sp_EliminarDetalleFactura(in codDF int)
		Begin
			Delete from DetalleFactura
            where codigoDetalleFactura = codDF;
		End $$
Delimiter ;
-- ------------------------------Editar detalle factura-------------------------------------------------------------------
Delimiter $$
	create procedure sp_EditarDetalleFactura(detalleDF int,precioDF decimal(10,2), cantDF int, numeroDF int, codigoDF varchar(15))
		Begin
			Update DetalleCompra DC
				set
				DF.precioUnitario = precioDF,
				DF.cantidad = cantDF,
				DF.factura_numeroFactura = numeroDF,
				DF.productos_codigoProducto = codigoDF
            where codigoDetalleFactura = detalleDF;
		End $$
Delimiter ;


