package io.robusta.classify;

import io.robusta.classify.domain.Ad;
import io.robusta.classify.domain.Admin;
import io.robusta.classify.domain.Category;
import io.robusta.classify.domain.Section;
import io.robusta.classify.domain.Tag;
import io.robusta.classify.domain.User;
import io.robusta.rra.resource.ResourceCollectionWrapper;
import io.robusta.rra.resource.ResourceList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dev on 29/08/14.
 */
public class ClassifyDataSource {

    private static ClassifyDataSource instance;

    private ClassifyDataSource() {
        initUsers();
        initSectionsAndCategorie();
        initTags();
        initAds();
    }

    public static ClassifyDataSource getInstance() {
        if ( instance == null ) {
            instance = new ClassifyDataSource();
        }
        return instance;
    }

    ResourceList<Long, User> users      = new ResourceList<Long, User>();
    List<Section>            sections   = new ArrayList<Section>();
    List<Category>           categories = new ArrayList<Category>();
    ResourceList<Long, Tag>  tags       = new ResourceList<Long, Tag>();
    List<Ad>                 ads        = new ArrayList<Ad>();

    public ResourceList<Long, User> getUsers() {
        return this.users;
    }

    public void setUsers( ResourceList<Long, User> users ) {
        this.users = users;
    }

    public List<Section> getSections() {
        return sections;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public ResourceList<Long, Tag> getTags() {
        return tags;
    }

    public List<Ad> getAds() {
        return ads;
    }

    private void initUsers() {
        User nicolas = new Admin( 1L, "nz@robusta.io", "Nicolas", "Star Wars rocks !" );
        User leonard = new Admin( 2L, "leonard@robusta.io", "Leonard", "Star Trek rocks" );
        User sheldon = new User( 3L, "sheldon@robusta.io", "Sheldon" );
        User raj = new User( 4L, "raj@robusta.io", "Raj" );
        User howard = new User( 5L, "howard@robusta.io", "Howard" );
        User penny = new User( 6L, "penny@robusta.io", "Penny" );
        User emy = new User( 7L, "emy@robusta.io", "Emy" );
        User bernie = new User( 8L, "bernie@robusta.io", "Bernie" );

        Collections.addAll( this.users, nicolas, leonard, sheldon, raj, howard, penny, emy, bernie );

        // TODO : Add yourself
    }

    public User nicolas() {
        return this.getUsers().get( 0 );
    }

    public User leonard() {
        return this.getUsers().get( 1 );
    }

    public User sheldon() {
        return this.getUsers().get( 2 );
    }

    private void initSectionsAndCategorie() {

        Section countries = new Section( "countries" );
        Section starts = new Section( "starts" );
        Collections.addAll( this.sections, countries, starts );

        Category vehicles = new Category( 1, "Vehicles" );
        Category auto = new Category( 2, "Auto" );
        Category moto = new Category( 3, "Moto" );
        Category renault = new Category( 4, "Renault" );
        Category ferrari = new Category( 5, "Ferrari" );
        vehicles.addChildren( auto, moto );
        auto.addChildren( renault, ferrari );

        Category realEstate = new Category( 6, "Real Estate" );
        Category house = new Category( 7, "House" );
        Category appartment = new Category( 8, "Appartment" );
        realEstate.addChildren( house, appartment );

        Category stuff = new Category( 9, "Stuff" );
        Category electronics = new Category( 10, "Electronics" );
        Category culture = new Category( 11, "Culture" );
        Category music = new Category( 12, "Music" );
        Category books = new Category( 13, "Books" );
        Category cd = new Category( 14, "CD" );
        Category k7 = new Category( 15, "K7" );
        stuff.addChildren( electronics, culture );
        culture.addChildren( music, books );
        music.addChildren( cd, k7 );

        Category france = new Category( 16, "France" );
        Category usa = new Category( 17, "USA" );
        Category midi = new Category( 18, "Midi-Roussillon" );
        Category idf = new Category( 19, "Ile de France" );
        france.addChildren( idf, midi );

        starts.addCategories( vehicles, realEstate, stuff );
        countries.addCategories( france, usa );

        Collections.addAll( this.categories, vehicles, auto, moto, renault,
                ferrari, realEstate, house, appartment, stuff, electronics,
                culture, music, books, cd, k7, france, usa, midi, idf );

    }

    private void initTags() {

        Tag old = new Tag( 1, "old" );
        Tag lennon = new Tag( 2, "John Lennon" );
        Collections.addAll( this.tags, old, lennon );

    }

    private void initAds() {

        ResourceCollectionWrapper<Long, Category> wrappper = new ResourceCollectionWrapper<Long, Category>(
                this.categories );
        Ad r4 = new Ad( 1, nicolas(), "Super Renault 4", "Achetez, c'est super", 250, wrappper.getById( 4L ) );
        Ad megane = new Ad( 2, nicolas(), "Super Renault Megane Coupé", "Cabriolez, c'est top", 18000,
                wrappper.getById( 4L ) );
        Ad laFerrari = new Ad( 3, sheldon(), "LaFerrari", "Achetez, c'est super cher", 250000000, wrappper.getById( 5L ) );

        Ad condominium = new Ad( 4, leonard(), "Super Condominium", "Live rich", 145233256, wrappper.getById( 8L ) );
        Ad farm = new Ad( 5, leonard(), "FarmVille", "Authentic 3D farm", 25.5f, wrappper.getById( 7L ) );
        farm.addTags( this.tags.getById( 1L ) );

        Ad magicalMisteryTour = new Ad( 6, nicolas(), "Magical Mistery Tour", "Rooooool up !!!!", 18.5f,
                wrappper.getById( 14L ) );
        magicalMisteryTour.addTags( this.tags.getById( 2L ) );

        Collections.addAll( this.ads, r4, megane, laFerrari, condominium, farm, magicalMisteryTour );

    }

}
