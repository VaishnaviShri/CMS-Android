package crux.bphc.cms.fragments;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import crux.bphc.cms.R;
import helper.MyFileManager;
import set.Module;

public class MoreOptionsFragment extends BottomSheetDialogFragment {

    private static final String IS_DOWNLOAD = "download";
    private static final String COURSE_NAME = "coursename";
    private static final String MODULE_ID = "moduleid";

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private MyFileManager mFileManager;
    private Module courseModule;
    private int downloaded;
    private String courseName;
    private int moduleId;

    private MoreOptionsFragment.Option[] options;

    public static MoreOptionsFragment newInstance(MoreOptionsFragment.Option... options) {
        Bundle args = new Bundle();
        args.putParcelableArray("options", options);

        MoreOptionsFragment fragment = new MoreOptionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null)
        {
            options = (MoreOptionsFragment.Option[]) args.getParcelableArray("options");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more_options,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.more_options_list);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        for (MoreOptionsFragment.Option option : options) {
            arrayAdapter.add(option.optionText);
        }
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            if (getActivity().getClass().isAssignableFrom(MoreOptionsFragment.ActivityCallback.class))
                ((MoreOptionsFragment.ActivityCallback) getActivity()).onOptionSelect(position);
        });
    }

    public static class Option implements Parcelable {
        int id;
        String optionText;
        int drawableIcon;

        public Option(int id, String option_text, int drawable_icon) {
            this.id = id;
            this.optionText = option_text;
            this.drawableIcon = drawable_icon;
        }

        protected Option(Parcel in) {
            id = in.readInt();
            optionText = in.readString();
            drawableIcon = in.readInt();
        }

        public static final Creator<Option> CREATOR = new Creator<Option>() {
            @Override
            public Option createFromParcel(Parcel in) {
                return new Option(in);
            }

            @Override
            public Option[] newArray(int size) {
                return new Option[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.optionText);
            dest.writeInt(this.drawableIcon);
        }

        public String getOptionText() {
            return optionText;
        }

    }

     public interface ActivityCallback {
        void onOptionSelect(int id);
    }

    /**
     * This interface is provided only for convinience and is not directly used by
     * `MoreOptionsFragment`. It's intended to be implemented by the actual consumers of the
     * selected option like fragments or a helper Class for an activity.
     **/
    public interface OptionHandlerCallBack {
        void onOptionSelect(int id);
    }
}
