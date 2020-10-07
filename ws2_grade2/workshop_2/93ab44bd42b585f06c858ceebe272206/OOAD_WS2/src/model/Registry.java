package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Registry {
	private ArrayList<Member> members;
	private String filePath = "S:\\eclipse-workspace\\OOAD_WS2\\src\\model\\savedMembers.json";

	public Registry() {
		load();
	}
	
	private void load() {
		members = new ArrayList<Member>();
		ObjectMapper om = new ObjectMapper();
		try {
			List<String> allLines = Files.readAllLines(Paths.get(filePath));
			for (String line : allLines) {
				line = line.substring(0, line.length());
				Member mem  = om.readValue(line, Member.class);
				members.add(mem);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		ObjectMapper om = new ObjectMapper();
		String item;
		File output = new File(filePath);

		try {
			FileWriter writer = new FileWriter(output);
			for (Member m : members) {
				item = om.writeValueAsString(m);
				writer.write(item+"\n");
			}
			writer.flush();
			writer.close();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// CREATE
	public void createMember(String name, String pnumber, String pass) {
		
		//Get member_id of last entry 
		int size = members.size();
		String m_id = members.get(size-1).getMemberID();
		
		// add zeroes to beginning of mid_int
		int mid_int = (Integer.parseInt(m_id)+1);
		size = Integer.toString(mid_int).length();

		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i+size < 6; i++) {
			sb.append("0");
		}
		sb.append(Integer.toString(mid_int));
		
		Member member = new Member(name, pnumber, sb.toString(), pass);
		members.add(member);
	}
	
	public void deleteMember(Member mem) {
		members.remove(mem);
	}
	
	public ArrayList<Member> getMembers() {
		return this.members;
	}

	
}
