package br.edu.fatec.chamada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AlunoArrayAdapter extends ArrayAdapter<Object> {

	
	private final Context context;
	private final Object[] alunos;

	public AlunoArrayAdapter(Context context, Object[] values) {
		super(context, R.layout.dispositivo_aluno , values);
		this.context = context;
	    this.alunos = values;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    
	    View rowView = convertView;
	    
	    if (convertView == null) {
	    	rowView = inflater.inflate(R.layout.dispositivo_aluno, parent, false);
	    }
	    
	    TextView textViewFirstLine = (TextView) rowView.findViewById(R.id.firstLine);
	    TextView textViewSecondLine = (TextView) rowView.findViewById(R.id.secondLine);
	    
	    //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    Aluno aluno = (Aluno) alunos[position];
	    textViewFirstLine.setText(aluno.getNome());
	    textViewSecondLine.setText(aluno.getBluetoothMacAddress());
	    
	    // TODO: change the icon for Windows and iPhone
//	    String s = values[position];
//	    if (s.startsWith("iPhone")) {
//	      imageView.setImageResource(R.drawable.no);
//	    } else {
//	      imageView.setImageResource(R.drawable.ok);
//	    }

	    return rowView;
	  }

}
