from manim import *

class MerryChristmas(Scene):
    def construct(self):
        # define texts
        text_christmas = TextMobject('Merry Christmas !', color=RED)
        text_and = TextMobject('and')
        text_new_year = TextMobject('Happy New Year !!', color=YELLOW)

        # display texts
        self.wait(1)
        self.play(Write(text_christmas))
        self.wait(1)
        self.play(ReplacementTransform(text_christmas,text_and))
        self.wait(1)
        self.play(ReplacementTransform(text_and,text_new_year))
        self.wait(1)
