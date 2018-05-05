package com.example.rvadam.projets1.ListFlowsByRouterScreen;

/**
 * Created by rvadam on 18/02/2018.
 */

public class DisplayedListOfQueuesTest {
/*The following tests are related to the ListFlowsByRouterActivity, that's why there are also tests on the FlowDetailsFragment for the landscape mode
  Similar tests will be realized on the FlowDetailsFragment displayed by the FlowDetailsActivity */

    //Test that verify the information about the queues of a selected router in portrait mode and the selection of one of the queues
        //Information verification with changes
        //We select the same router (no changes)
        //Information verification with changes on queues'state
        //We click on one of the queues

        //We select another router
        //Information verification with changes on queues'state
        //We click on one of the queues

    //Test that verify the information about the queues of a selected router in landscape mode and the selection of one of the queues
        //No queues selected yet
        //We select the first queue
        //Information verification with changes on the selected queue on both fragment
        //We select the second queue
        //Information verification with changes on the selected queue on both fragment
        //We select the current router in the Router Spinner (no changes)
        //We select another router
        //We select the first queue
        //Information verification with changes on the selected queue on both fragment
}
