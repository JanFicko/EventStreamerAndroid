package xyz.eventstreamer.eventstreamer.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.Location;

public class MapView extends FrameLayout {

    private Subject<GoogleMap> mapSubject;
    private MarkerClickListener markerClickListener;

    public MapView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs,
                   @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();

        if (!isInEditMode()) {
            FragmentTransaction fragmentTransaction =
                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(getId(), mapFragment);
            fragmentTransaction.commit();

            mapSubject = BehaviorSubject.create();
            Observable.create(
                    (ObservableOnSubscribe<GoogleMap>) e -> mapFragment.getMapAsync(e::onNext))
                    .subscribe(mapSubject);
        }
    }

    public void setMarkerClickListener(MarkerClickListener markerClickListener){
        this.markerClickListener = markerClickListener;
    }

    @SuppressLint("CheckResult")
    public void addMarker(Event event, Location location) {
        mapSubject.subscribe(googleMap -> {
            LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(position))
                    .setTag(event);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));

            googleMap.setOnMarkerClickListener(marker -> {
                markerClickListener.onMarkerClick((Event)marker.getTag());
                return false;
            });
        });
    }
}