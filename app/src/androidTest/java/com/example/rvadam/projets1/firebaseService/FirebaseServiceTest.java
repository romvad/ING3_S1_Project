package com.example.rvadam.projets1.firebaseService;

/*import android.content.Intent;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;

import com.example.rvadam.projets1.model.Router;
import com.example.rvadam.projets1.services.FirebaseService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Iterator;
import java.util.concurrent.TimeoutException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.powermock.api.mockito.PowerMockito;*/

/*import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import android.support.test.InstrumentationRegistry;
import android.support.test.InstrumentationRegistry;

import java.util.Iterator;

import static org.mockito.Mockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Matchers.any;*/
/**
 * Created by rvadam on 07/02/2018.
 */

/*@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})*/
public class FirebaseServiceTest {

  /* private static FirebaseDatabase mockedFirebaseDatabase;
    private static DatabaseReference mockedDatabaseReference;

    private static Router r1;
    private static Router r2;

    @Rule
    public ServiceTestRule mServiceRule=new ServiceTestRule ();

    @Before
    public void prepareMockDatabaseReference(){
        mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);

        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
    }

    @Test
    public void testOnDataChangeRouterNodeWithNoRouterPreviouslyStored(){
        when(mockedFirebaseDatabase.getReference("routers")).thenReturn(mockedDatabaseReference);

        r1=new Router("1","router1");
        r2=new Router("2","router2");
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];

                DataSnapshot mockedRouterDataSnapshot = Mockito.mock(DataSnapshot.class);
                Iterable<DataSnapshot> mockedIterable = Mockito.mock(Iterable.class);
                Iterator<DataSnapshot> mockedIterator = Mockito.mock(Iterator.class);
                DataSnapshot snapshot1 = Mockito.mock(DataSnapshot.class);
                DataSnapshot snapshot2 = Mockito.mock(DataSnapshot.class);
                when(mockedRouterDataSnapshot.getChildren()).thenReturn(mockedIterable);
                when(mockedIterable.iterator()).thenReturn(mockedIterator);
                when(mockedIterator.hasNext()).thenReturn(true, true, false);
                when(mockedIterator.next()).thenReturn(snapshot1, snapshot2);
                when(snapshot1.getValue(Router.class)).thenReturn(r1);
                when(snapshot2.getValue(Router.class)).thenReturn(r2);

                valueEventListener.onDataChange(mockedRouterDataSnapshot);
                return null;
            }
        }).when(mockedDatabaseReference).addValueEventListener((any(ValueEventListener.class)));

        IBinder binder = null;
        try {
            binder = mServiceRule.bindService(
                    new Intent(InstrumentationRegistry.getTargetContext(), FirebaseService.class));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        FirebaseService service = ((FirebaseService.LocalBinder) binder).getService();

        try {
            mServiceRule.startService(
                    new Intent(InstrumentationRegistry.getTargetContext(), FirebaseService.class));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }*/

}
