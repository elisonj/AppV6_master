package br.com.bg7.appvistoria.data.inspection;

import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.bg7.appvistoria.data.Inspection;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.callback.SyncCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.PictureResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.ProductResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;

/**
 * Created by: luciolucio
 * Date: 2017-08-17
 */

class InspectionTestBase {

    Inspection inspection;

    @Mock
    PictureService pictureService;

    @Mock
    InspectionService inspectionService;

    @Captor
    ArgumentCaptor<HttpProgressCallback<ProductResponse>> productServiceCallback;

    @Captor
    ArgumentCaptor<HttpProgressCallback<PictureResponse>> pictureServiceCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        inspection = new Inspection();
    }

    void startPictureSync() {
        inspection.sync(pictureService, new EmptySyncCallback());
    }

    class EmptySyncCallback implements SyncCallback {

        @Override
        public void onSuccess(Inspection inspection) {

        }

        @Override
        public void onProgressUpdated(Inspection inspection, double progress) {

        }

        @Override
        public void onFailure(Inspection inspection, Throwable t) {

        }
    }
}
