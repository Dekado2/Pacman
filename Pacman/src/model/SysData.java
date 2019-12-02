package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import utils.Constants;
import utils.E_DifficultyLevel;

public class SysData {

	/**
	 *  single instance of SysData at a time
	 */
	private static SysData instance;
	/**
	 *  list of questions
	 */
	private ArrayList<Question> questionsList;
	/**
	 *  list of top 10 players in leaderboard - nickname/score/level/time/date
	 */
	private TreeSet<Leaderboard> leaderboardList;


	/**
	 * SysData Constructor
	 */
	private SysData()
	{
		questionsList = new ArrayList<Question>();
		leaderboardList = new TreeSet<Leaderboard>();
		loadQuestions();
		loadLeaderboardData();
	}

	/**
	 * Singleton class- 1 instance of SysData at a time
	 * @return
	 */
	public static SysData getInstance()
	{
		if (instance == null)
			instance = new SysData();
		return instance;
	}

	/**
	 *  read leaderboard data from file
	 * @return true if succeeded
	 */
	public boolean loadLeaderboardData()
	{
		JSONParser parser=new JSONParser();
		try (
				    FileReader is = new FileReader("leaderboard.json");
					BufferedReader reader = new BufferedReader(is)) {
					JSONObject arr = (JSONObject)parser.parse(reader);
					JSONArray arry = (JSONArray)arr.get("topPlayers");
					for (Object obj : arry){
						JSONObject lb=(JSONObject)obj;
						String pName = (String)lb.get("nickname");
						String s = String.valueOf(lb.get("score"));
						int score = Integer.parseInt(s);
						String l = String.valueOf(lb.get("level"));
						int level = Integer.parseInt(l);
						String d = (String)lb.get("gameDuration");
					    Timer t = new Timer (0, 0 , false);
					    t.setElapsed(t.toMillis(d));
						PacmanPlayer p = new PacmanPlayer(null, pName, score, 0, level, t);
						String date = (String)lb.get("date");
						//create the question - id will be the index
						Leaderboard result = new Leaderboard(p, date);
						//add the player game result to the list
						leaderboardList.add(result);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 *  write leaderboard data to file
	 * @return
	 */
	public boolean saveLeaderboardData()
	{
		JSONObject obj = new JSONObject();

		JSONArray lbs = new JSONArray();

		for(Leaderboard lb : leaderboardList){
			JSONObject obj2 = new JSONObject();
			obj2.put("nickname", lb.getPlayer().getNickName());
			obj2.put("score", lb.getPlayer().getPoints());
			obj2.put("level", lb.getPlayer().getLevel());
			obj2.put("gameDuration", lb.getPlayer().getGameLength().getElapsed());
			obj2.put("date", lb.getDate());
			lbs.add(obj2);

		}
		obj.put("topPlayers", lbs);

		try (FileWriter file = new FileWriter("leaderboard.json")) {
			file.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 *  read questions from json
	 * @return true if readed successfully
	 */
	public boolean loadQuestions()
	{
		
		JSONParser parser=new JSONParser();
		try (
				    FileReader is = new FileReader("questions.json");
					BufferedReader reader = new BufferedReader(is)) {
					JSONObject arr = (JSONObject)parser.parse(reader);
					JSONArray arry = (JSONArray)arr.get("questions");
					for (Object obj : arry){
						JSONObject q=(JSONObject)obj;
						String qName = (String)q.get("question");
						String l = String.valueOf(q.get("level"));
						int level = Integer.parseInt(l);
						E_DifficultyLevel levelName=E_DifficultyLevel.EASY;
						levelName=levelName.toStringDifficultyLevel(level);
						String team = (String)q.get("team");
						//create the question - id will be the index
						Question q1 = new Question(team, qName, levelName);
						//create answers and add them to the question
						String c = String.valueOf(q.get("correct_ans"));
						int correctAns = Integer.parseInt(c);
						JSONArray answers =(JSONArray)q.get("answers");
						for(int i=0; i<answers.size(); i++){
							String ans= answers.get(i).toString();
							if(i==correctAns){
								Answer a = new Answer(i+1, ans);
								q1.addAnswer(a);
								q1.setCorrectAnswer(a);
								
							}
							else {
								Answer a = new Answer(i+1, ans);
								q1.addAnswer(a);
							}
						}
						//add the question to the list
						questionsList.add(q1);
			}
		}
		catch (Exception e) {
			 e.printStackTrace();
		}

		return true;
	}

	/**
	 *  save questions to Json
	 * @return return true if saved questions successfuly
	 */
	@SuppressWarnings("unchecked")
	public boolean saveQuestions()
	{
		JSONObject obj = new JSONObject();

		JSONArray qs = new JSONArray();

		int ca=0;

		for(Question q : questionsList){
			JSONObject obj2 = new JSONObject();
			obj2.put("question", q.getQText());

			JSONArray as = new JSONArray();
			for(Answer a : q.getPossibleAnswers()){

				if(a.equals(q.getCorrectAnswer()))

					ca=q.getPossibleAnswers().indexOf(a);
				as.add(a.getAText());
			}
			obj2.put("answers", as);
			obj2.put("correct_ans", ca);
			obj2.put("level", q.getDifficultyLevel().getDiff());
			obj2.put("team", q.getTeamName());
			qs.add(obj2);

		}
		obj.put("questions", qs);

		try (FileWriter file = new FileWriter("questions.json")) {
			file.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 *  add a new question
	 * @param qName
	 * @param diffLevel
	 * @param aText
	 * @param bText
	 * @param cText
	 * @param dText
	 * @param correctAns
	 * @return
	 */
	public boolean addQuestion(String qName,String diffLevel,String aText,String bText,String cText,String dText,String correctAns)
	{
		Answer correct=null;
		String team = "Viper";
		int i=1;
		int level= E_DifficultyLevel.valueOf(diffLevel).getDiff();
		E_DifficultyLevel levelName = E_DifficultyLevel.EASY;
		levelName=levelName.toStringDifficultyLevel(level);
		//create the question - id will be the index
		Question q1 = new Question(team, qName, levelName);
		if(aText!=null && !aText.isEmpty()){
			Answer a= new Answer(i++, aText);
			q1.addAnswer(a);
			if(a.getAID()==Integer.parseInt(correctAns))
				correct= new Answer(a.getAID(), a.getAText());
		}
		if(bText!=null && !bText.isEmpty()){
			Answer b= new Answer(i++, bText);
			q1.addAnswer(b);
			if(b.getAID()==Integer.parseInt(correctAns))
				correct= new Answer(b.getAID(),  b.getAText());
		}
		if(cText!=null && !cText.isEmpty()){
			Answer c= new Answer(i++, cText);
			q1.addAnswer(c);
			if(c.getAID()==Integer.parseInt(correctAns))
				correct= new Answer(c.getAID(),  c.getAText());
		}
		if(dText!=null && !dText.isEmpty()){
			Answer d= new Answer(i, dText);
			q1.addAnswer(d);
			if(d.getAID()==Integer.parseInt(correctAns))
				correct= new Answer(d.getAID(),  d.getAText());
		}
		
		q1.setCorrectAnswer(correct);
		if(questionsList.add(q1)) {
			saveQuestions();
			return true;
		}
		return false;
	}

	/**
	 *  edit an existing question
	 * @param qID
	 * @param qName
	 * @param diffLevel
	 * @param aText
	 * @param bText
	 * @param cText
	 * @param dText
	 * @param correctAns
	 * @return
	 */
	public boolean editQuestion(int qID,String qName,String diffLevel,String aText,String bText,String cText,String dText,String correctAns)
	{
		Answer correct=null;
		String team = "Viper";
		int i=1;
		int level= E_DifficultyLevel.valueOf(diffLevel).getDiff();
		E_DifficultyLevel levelName = E_DifficultyLevel.EASY;
		levelName=levelName.toStringDifficultyLevel(level);
		//create the question - id will be the index
		Question q1 = new Question(team, qName, levelName);
		if(aText!=null && !aText.isEmpty()){
			Answer a= new Answer(i++, aText);
			q1.addAnswer(a);
			if(a.getAID()==Integer.parseInt(correctAns))
				correct= new Answer(a.getAID(), a.getAText());
		}
		if(bText!=null && !bText.isEmpty()){
			Answer b= new Answer(i++, bText);
			q1.addAnswer(b);
			if(b.getAID()==Integer.parseInt(correctAns))
				correct= new Answer(b.getAID(),  b.getAText());
		}
		if(cText!=null && !cText.isEmpty()){
			Answer c= new Answer(i++, cText);
			q1.addAnswer(c);
			if(c.getAID()==Integer.parseInt(correctAns))
				correct= new Answer(c.getAID(),  c.getAText());
		}
		if(dText!=null && !dText.isEmpty()){
			Answer d= new Answer(i, dText);
			q1.addAnswer(d);
			if(d.getAID()==Integer.parseInt(correctAns))
				correct= new Answer(d.getAID(),  d.getAText());
		}
		
		q1.setCorrectAnswer(correct);
		
		if(questionsList.add(q1)) {
			saveQuestions();
			return true;
		}
		return false;
	}
	

	/**
	 *  remove an existing question
	 * @param id - question's id
	 * @return true if removed successfully
	 */
	public boolean removeQuestion(int id)
	{
		
		Question toRemove = new Question(id);
		int i=questionsList.indexOf(toRemove);
		questionsList.remove(questionsList.get(i));
		saveQuestions();
		return true;
		 
	}

	/**
	 *  checks if the stats of the player at the end of the game are better than any in the leaderboard
	 * @return true id game ended
	 */
	public boolean checkEndGameStats(Leaderboard result)
	{
		if (leaderboardList.size()<Constants.LEADERBOARD_LIST_LENGTH)
			return true;
		else if (leaderboardList.size()>=Constants.LEADERBOARD_LIST_LENGTH)
		{
			int res = result.compareTo(leaderboardList.last());
			if (res==-1)
				return true;
		}
		return false;
	}
	/**
	 *  if checkEndGameStats() returns true, add the game stats to the leaderboard and use removeGame() to remove the worst one
	 * @param result
	 */
	public void addGame(Leaderboard result)
	{
       leaderboardList.add(result);
	}

	/**
	 *  remove a no longer relevant game score from the leaderboard
	 */
	public void removeGame()
	{
		if (leaderboardList.size()>=Constants.LEADERBOARD_LIST_LENGTH)
       leaderboardList.remove(leaderboardList.last());
	}

	public ArrayList<Question> getQuestionsList() {
		return questionsList;
	}

	public TreeSet<Leaderboard> getLeaderboardList() {
		return leaderboardList;
	}

}
