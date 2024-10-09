package se331.lab.rest.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.lab.rest.entity.Event;
import se331.lab.rest.repository.EventRepository;

@Repository
@RequiredArgsConstructor
@Profile("db")
public class EventDaoDbImpl implements EventDao {
    final EventRepository eventRepository;

    @Override
    public Integer getEventSize() {
        return Math.toIntExact(eventRepository.count());
    }

    @Override
    public Page<Event> getEvents(Integer pageSize, Integer page) {
        return eventRepository.findAll(PageRequest.of(page != null ? page - 1 : 0, pageSize));
    }

    @Override
    public Page<Event> getEvents(String title, String description, Pageable page) {
        return eventRepository.findByTitleContainingOrDescriptionContaining(title, description, page);
    }

    @Override
    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Page<Event> getEvents(String title, String description, String organizerName, Pageable page) {
        return eventRepository.findByTitleContainingOrDescriptionIgnoreCaseContainingOrOrganizer_NameIgnoreCaseContaining(title, description, organizerName, page);
    }
}

