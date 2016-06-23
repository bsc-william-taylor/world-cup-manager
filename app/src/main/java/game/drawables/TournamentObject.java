package game.drawables;

import framework.IFactory;
import framework.IRenderable;
import framework.graphics.Button;
import framework.graphics.Font;
import framework.graphics.Image;
import framework.opengl.OpenglLine;
import game.objects.Globals;
import game.objects.MatchResult;
import android.view.MotionEvent;
import game.objects.Team;
import game.events.KnockOutEvent;
import framework.core.*;

import java.util.ArrayList;

public class TournamentObject implements IRenderable {
    private class TeamItem {
        public ClickEvent listener;
        public Button button;
        public Image image;

        public KnockOutEvent event;
        public Team team;
    }

    private ArrayList<TeamItem> roundOfSixteen = new ArrayList<TeamItem>();
    private ArrayList<TeamItem> quaterfinals = new ArrayList<TeamItem>();
    private ArrayList<TeamItem> semifinals = new ArrayList<TeamItem>();
    private ArrayList<TeamItem> finals = new ArrayList<TeamItem>();
    private Progress progressLineRight = new Progress();
    private Progress progressLineLeft = new Progress();
    private Boolean reloaded = true;
    private Button winnerHeader;
    private Button winnerFooter;
    private Integer resultsPosted;

    public TournamentObject() {
        Font font = Font.get("tiny");

        winnerFooter = new Button(font);
        winnerFooter.setSprite("sprites/fill.png", 480, 150, 310, 50);
        winnerFooter.setText("Brazil", 635, 165);
        winnerFooter.setTextColour(1F, 1F, 0F, 1F);

        winnerHeader = new Button(font);
        winnerHeader.setSprite("sprites/fill.png", 480, 550, 310, 50);
        winnerHeader.setText("Winner", 640, 560, 200, 80);
        winnerHeader.setTextColour(1F, 1F, 0F, 1F);

        resultsPosted = 0;

        float[] y = {
            625f, 550f, 475f, 400f, 325f, 250f, 175f, 100f,
            625f, 550f, 475f, 400f, 325f, 250f, 175f, 100f,
        };

        float[] y2 = { 575f, 450f, 275f, 150f, 575f, 450f, 275f, 150f, };
        float[] y3 = { 480F, 260F, 480F, 260F };
        float[] y4 = { 375F, 375F };

        for(int i = 0; i < 16; i++) {
            TeamItem item = new TeamItem();

            item.image = new Image("sprites/" + "empty" + ".bmp");
            item.button = new Button(font);

            if(i >= 8) {
                item.button.setSprite("sprites/button.png", 1145, (int)y[i], 50, 50);
                item.image.setPosition(1205, (int)y[i - 8], 75, 50);
            } else {
                item.button.setSprite("sprites/button.png", 90, (int)y[i], 50, 50);
                item.image.setPosition(0, (int)y[i], 75, 50);
            }

            item.listener = new ClickEvent(item.button);
            roundOfSixteen.add(item);
        }

        for(int i = 0; i < 8; i++) {
            TeamItem item = new TeamItem();
            item.image = new Image("sprites/" + "empty" + ".bmp");
            item.button = new Button(font);

            if(i >= 4) {
                item.button.setSprite("sprites/button.png", 955, (int)y2[i], 50, 50);
                item.image.setPosition(1015, (int)y2[i], 75, 50);
            } else {
                item.button.setSprite("sprites/button.png", 275, (int)y2[i], 50, 50);
                item.image.setPosition(185, (int)y2[i], 75, 50);
            }

            item.listener = new ClickEvent(item.button);
            quaterfinals.add(item);
        }

        for(int i = 0; i < 8; i += 2) {
            TeamItem current = quaterfinals.get(i);
            TeamItem next = quaterfinals.get(i+1);

            KnockOutEvent event = new KnockOutEvent(current.button, next.button, next.team, current.team, 1);

            event.ReverseOrder();

            current.listener.eventType(event);
            current.event = event;

            next.listener.eventType(event);
            next.event = event;
        }

        for(int i = 0; i < 4; i++) {
            TeamItem Item = new TeamItem();
            Item.image = new Image("sprites/" + "empty" + ".bmp");
            Item.button = new Button(font);

            if(i >= 2) {
                Item.button.setSprite("sprites/button.png", 780, (int)y3[i], 50, 50);
                Item.image.setPosition(850, (int)y3[i], 75, 50);
            } else {
                Item.button.setSprite("sprites/button.png", 445, (int)y3[i], 50, 50);
                Item.image.setPosition(355, (int)y3[i], 75, 50);
            }

            Item.listener = new ClickEvent(Item.button);
            semifinals.add(Item);
        }

        for(int i = 0; i < 4; i += 2) {
            TeamItem current = semifinals.get(i);
            TeamItem next = semifinals.get(i+1);

            KnockOutEvent event = new KnockOutEvent(current.button, next.button, null, null, 2);

            event.ReverseOrder();

            current.listener.eventType(event);
            current.event = event;

            next.listener.eventType(event);
            next.event = event;
        }

        for(int i = 0; i < 2; i++) {
            TeamItem item = new TeamItem();
            item.image = new Image("sprites/" + "empty" + ".bmp");
            item.button = new Button(Font.get("tiny"));

            if(i >= 1) {
                item.button.setSprite("sprites/button.png", 675, (int)y4[i], 50, 50);
                item.image.setPosition(730, (int)y4[i], 75, 50);
            } else {
                item.button.setSprite("sprites/button.png", 550, (int)y4[i], 50, 50);
                item.image.setPosition(470, (int)y4[i], 75, 50);
            }

            item.listener = new ClickEvent(item.button);
            finals.add(item);
        }

        for(int i = 0; i < 2; i += 2) {
            TeamItem current = finals.get(i);
            TeamItem next = finals.get(i+1);

            KnockOutEvent event = new KnockOutEvent(current.button, next.button, null, null, 3);

            event.ReverseOrder();

            current.listener.eventType(event);
            current.event = event;

            next.listener.eventType(event);
            next.event = event;
        }
    }

    public void onEnter() {
        if(Globals.get().getKnockOutResults().size() == 0 || reloaded) {
            Globals g = Globals.get();
            reloaded = false;
            String[] id = {
                "A", "B", "C", "D",
                "E", "F", "G", "H",
                "B", "A", "D", "C",
                "F", "E", "H", "G",

            };

            for(int i = 0; i < 16; i++) {
                TeamItem team = roundOfSixteen.get(i);

                if(i % 2 == 0) {
                    team.team = g.getWinner(id[i]);
                } else {
                    team.team = g.getRunnerUp(id[i]);
                }

                team.image.setTexture("sprites/" + team.team.getName() + ".bmp");
            }


            for(int i = 0; i < 16; i += 2) {
                TeamItem Next = roundOfSixteen.get(i+1);
                TeamItem Item = roundOfSixteen.get(i);

                Next.event = new KnockOutEvent(Next.button, Item.button, Next.team, Item.team, 0);

                Item.listener.eventType(Next.event);
                Next.listener.eventType(Next.event);
            }
        }

        EventManager events = EventManager.get();
        for(int i = 0; i < 16; i += 2) {
            TeamItem next = roundOfSixteen.get(i+1);
            TeamItem item = roundOfSixteen.get(i);

            events.addListener(item.listener);
            events.addListener(next.listener);

            if(i < 2) {
                TeamItem current = finals.get(i);
                next = finals.get(i+1);

                events.addListener(current.listener);
                events.addListener(next.listener);
            }

            if(i < 4) {
                TeamItem current = semifinals.get(i);
                next = semifinals.get(i+1);

                events.addListener(current.listener);
                events.addListener(next.listener);
            }

            if(i < 8) {
                TeamItem current = quaterfinals.get(i);
                next = quaterfinals.get(i+1);

                events.addListener(current.listener);
                events.addListener(next.listener);
            }
        }
    }

    public void onExit() {
        EventManager events = EventManager.get();

        for(int i = 0; i < 16; i += 2) {
            TeamItem Next = roundOfSixteen.get(i+1);
            TeamItem Item = roundOfSixteen.get(i);

            events.removeListener(Item.listener);
            events.removeListener(Next.listener);
        }

        for(int i = 0; i < 2; i += 2) {
            TeamItem current = finals.get(i);
            TeamItem next = finals.get(i+1);

            events.removeListener(current.listener);
            events.removeListener(next.listener);
        }

        for(int i = 0; i < 4; i += 2) {
            TeamItem current = semifinals.get(i);
            TeamItem next = semifinals.get(i+1);

            events.removeListener(current.listener);
            events.removeListener(next.listener);
        }

        for(int i = 0; i < 8; i += 2) {
            TeamItem current = quaterfinals.get(i);
            TeamItem next = quaterfinals.get(i+1);

            events.removeListener(current.listener);
            events.removeListener(next.listener);
        }
    }

    public void add(ArrayList<Object> objects) {
        if(Globals.get().getKnockOutResults().size() == 15) {
            objects.add(winnerHeader);
            objects.add(winnerFooter);
        }

        progressLineRight.add(objects);
        progressLineLeft.add(objects);

        for(TeamItem team : roundOfSixteen) {
            if(team.image.getFilename() != "sprites/empty.bmp") {
                objects.add(team.button);
                objects.add(team.image);
            }
        }

        for(TeamItem team : semifinals) {
            if(team.image.getFilename() != "sprites/empty.bmp") {
                objects.add(team.button);
                objects.add(team.image);
            }
        }

        for(TeamItem team : finals) {
            if(team.image.getFilename() != "sprites/empty.bmp") {
                objects.add(team.button);
                objects.add(team.image);
            }
        }

        for(TeamItem team : quaterfinals) {
            if(team.image.getFilename() != "sprites/empty.bmp") {
                objects.add(team.button);
                objects.add(team.image);
            }
        }
    }

    public void initialise(IFactory factory) {
        progressLineRight.setPosition(1280, 650, -170, 575, true);
        progressLineLeft.setPosition(0, 650, 170, 575, false);
    }

    public void onTouchEvent(MotionEvent e, int x, int y) {
        if(e.getAction() == MotionEvent.ACTION_DOWN) {
            for(TeamItem team : roundOfSixteen) {
                team.listener.OnTouch(e, x, y);
            }

            for(TeamItem team : quaterfinals) {
                team.listener.OnTouch(e, x, y);
            }

            for(TeamItem team : semifinals) {
                team.listener.OnTouch(e, x, y);
            }

            for(TeamItem team : finals) {
                team.listener.OnTouch(e, x, y);
            }
        }
    }

    public void updateListeners(Integer number) {
        MatchResult result = Globals.get().getKnockOutResults().get(number);
        String Winner = result.getWinnersName();
        Integer Location = -1;

        for(int i = 0; i < roundOfSixteen.size(); i++) {
            if(result.getWinnersName().compareToIgnoreCase(roundOfSixteen.get(i).team.teamName) == 0) {
                Location = i;
                break;
            }
        }

        if(Location != -1) {
            int current = (Location/2);

            TeamItem last = roundOfSixteen.get(Location);
            TeamItem item = quaterfinals.get(current);

            if(item.image.getFilename().compareToIgnoreCase("sprites/" + Winner + ".bmp") == 0) {
                TeamItem team = semifinals.get(current/2);

                if(team.image.getFilename().compareToIgnoreCase("sprites/" + Winner + ".bmp") == 0) {
                    TeamItem fin;

                    if(Location < 8) {
                        fin = finals.get(0);
                        fin.image.setTexture("sprites/" + Winner + ".bmp");
                        fin.event.SetTeam(team.team, 0);
                        fin.team = last.team;


                    } else {
                        fin = finals.get(1);
                        fin.image.setTexture("sprites/" + Winner + ".bmp");
                        fin.event.SetTeam(team.team, 1);
                        fin.team = last.team;
                    }

                    loadResultFromFile(3, Winner, fin);

                    if(number+1 == 15) {
                        winnerFooter.setText(Winner, 635, 160);
                        winnerFooter.setTextColour(1F, 1F, 0F, 1F);
                    }

                } else {
                    team.image.setTexture("sprites/" + Winner + ".bmp");
                    team.event.SetTeam(last.team, current/2);
                    team.team = last.team;

                    loadResultFromFile(2, Winner, team);
                }
            } else {
                item.image.setTexture("sprites/" + Winner + ".bmp");
                item.event.SetTeam(last.team, current);
                item.team = last.team;

                loadResultFromFile(1, Winner, item);
            }
        }
    }

    private void loadResultFromFile(Integer type, String Winner, TeamItem item) {
        ArrayList<MatchResult> results = Globals.get().getKnockOutResults();
        for(int i = 0; i < results.size(); i++) {
            MatchResult newResult = results.get(i);
            if(newResult.type == type) {
                if(newResult.teamOne.compareToIgnoreCase(Winner) == 0 || newResult.teamTwo.compareToIgnoreCase(Winner) == 0) {
                    item.event.triggerEvent(newResult);
                }
            }
        }
    }

    public void update() {
        int numberOfResults = Globals.get().getKnockOutResults().size();

        while(resultsPosted != numberOfResults) {
            updateListeners(resultsPosted);
            resultsPosted++;
        }

        progressLineRight.update();
        progressLineLeft.update();

        for(TeamItem team : quaterfinals) {
            team.button.update();
            team.image.update();
        }

        for(TeamItem team : finals) {
            team.button.update();
            team.image.update();
        }

        for(TeamItem team : semifinals) {
            team.button.update();
            team.image.update();
        }

        for(TeamItem team : roundOfSixteen) {
            team.button.update();
            team.image.update();
        }

        if(numberOfResults == 15) {
            winnerHeader.update();
            winnerFooter.update();
        }
    }

    public void reset() {
        resultsPosted = 0;
        reloaded = true;

        for(TeamItem item : quaterfinals) {
            item.image.setTexture("sprites/empty.bmp");
            item.button.hideText();

            if(item.event != null) {
                item.event.Reset();
            }
        }

        for(TeamItem item : semifinals) {
            item.image.setTexture("sprites/empty.bmp");
            item.button.hideText();

            if(item.event != null) {
                item.event.Reset();
            }
        }

        for(TeamItem item : finals) {
            item.image.setTexture("sprites/empty.bmp");
            item.button.hideText();

            if(item.event != null) {
                item.event.Reset();
            }
        }

        for(TeamItem item : roundOfSixteen) {
            item.button.hideText();
            if(item.event != null) {
                item.event.Reset();
            }
        }
    }

    @Override
    public void render() {
        progressLineRight.render();
        progressLineLeft.render();

        for(TeamItem team : quaterfinals) {
            if(team.image.getFilename() != "sprites/empty.bmp") {
                team.button.render();
                team.image.render();
            }
        }

        for(TeamItem team : finals) {
            if(team.image.getFilename() != "sprites/empty.bmp") {
                team.button.render();
                team.image.render();
            }
        }

        for(TeamItem team : semifinals) {
            if(team.image.getFilename() != "sprites/empty.bmp") {
                team.button.render();
                team.image.render();
            }
        }

        for(TeamItem team : roundOfSixteen) {
            if(team.image.getFilename() != "sprites/empty.bmp") {
                team.button.render();
                team.image.render();
            }
        }

        if(Globals.get().getKnockOutResults().size() == 15) {
            winnerHeader.render();
            winnerFooter.render();
        }
    }

    public class Progress implements IRenderable {
        private OpenglLine[] teamLines = new OpenglLine[14];

        public void setPosition(int x, int y, int width, int height, boolean reversed) {
            float[] data = new float[8];

            // if its reversed the width will be negative
            if(!reversed) {
                 data[0] = x;	data[2] = width;
                 data[1] = y;	data[4] = width;
                 data[3] = y;	data[5] = height;
                 data[6] = x;	data[7] = height;
            } else {
                 data[0] = x;	data[2] = x + width;
                 data[1] = y;	data[4] = x + width;
                 data[3] = y;	data[5] = height;
                 data[6] = x;	data[7] = height;
            }

            float yelement = 50.0f;
            int elements = 4;

            // Create each line and set the position & width
            for(int z = 0; z < 7; z += elements) {
                float[] temp = data.clone();
                if(z > 0 && elements > 1) {
                    elements /= 2;
                }

                for(int i = z; i < (z + elements); i++) {
                    teamLines[i] = new OpenglLine();
                    teamLines[i].setColour(1f, 1f, 1f, 1f);
                    teamLines[i].setPosition(temp);
                    teamLines[i].setLineWidth(5.0f);

                    float value = 0.0f;
                    switch(z) {
                        case 0: value = 150.0f; break;
                        case 4: value = 300.0f; break;
                        default: value = 0.0f; break;
                    }

                    temp[1] -= value;	temp[3] -= value;
                    temp[5] -= value;	temp[7] -= value;
                }

                data[0] += width;		data[2] += width;
                data[4] += width;		data[6] += width;

                if(z < 6) {
                    data[5] -= yelement*2;	data[7] -= yelement*2;
                    data[1] -= yelement;	data[3] -= yelement;

                    yelement += yelement;
                }
            }

            float Height =  data[1] - (data[3]/2);

            teamLines[7] = new OpenglLine();
            teamLines[7].setLineWidth(5.0f);
            teamLines[7].setPosition(data[0], Height, data[0] + 100, Height);
            teamLines[7].setColour(1f, 1f, 1f, 1f);
        }

        public void add(ArrayList<Object> objects) {
            for(int i = 0; i < teamLines.length; i++) {
                objects.add(teamLines[i]);
            }
        }

        public void update() {
            for(int i = 0; i < 7; i++) {
                teamLines[i].update();
            }
        }

        @Override
        public void render() {
            for(int i = 0; i < 7; i++) {
                teamLines[i].render();
            }
        }
    }
}
