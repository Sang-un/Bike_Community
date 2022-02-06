package bike.community.controller;

import bike.community.model.network.Header;

public interface CrudControllerInterface<Request, Response> {

    Header<Response> create(Request request);

    Header<Response> read(Request request);

    Header<Response> update(Request request);

    Header delete(Request request);
}
