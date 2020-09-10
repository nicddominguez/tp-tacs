package tp.tacs.api.daos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tp.tacs.api.dominio.usuario.Usuario;

public class UsuarioDaoTest {

    private UsuarioDao dao;
    private Usuario usuario;
    private Usuario usuario1;

    @Before
    public void before() {
        dao = new UsuarioDao();
        usuario = new Usuario(1L, "usuario@gmail.com", "Usuario");
        usuario1 = new Usuario(2L, "usuario1@gmail.com", "Usuario1");
    }

    @Test
    public void getNull() {
        Assert.assertNull(dao.get(1L));
    }

    @Test
    public void save() {
        dao.save(usuario);
    }

    @Test
    public void getAll() {
        dao.save(usuario);
        Usuario daoUsuario = dao.getAll().get(0);
        Assert.assertTrue(daoUsuario.getId().equals(usuario.getId()) && daoUsuario.getNombre().equals(usuario.getNombre()) && daoUsuario.getMail()
                .equals(usuario.getMail()));
    }

    @Test
    public void delete() {
        dao.save(usuario);
        dao.delete(usuario);
        Assert.assertEquals(0,dao.getAll().size());
    }
}