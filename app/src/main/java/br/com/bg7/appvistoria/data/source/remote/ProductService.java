package br.com.bg7.appvistoria.data.source.remote;

import java.util.List;

import br.com.bg7.appvistoria.projectselection.vo.Product;

/**
 * Created by: elison
 * Date: 2017-08-31
 */
public interface ProductService {
    List<Product> findByProjectAndAddress(String project);
}
