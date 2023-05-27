import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './module-1.reducer';

export const Module1Detail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const module1Entity = useAppSelector(state => state.module1.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="module1DetailsHeading">
          <Translate contentKey="eduApp.module1.detail.title">Module1</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{module1Entity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.module1.title">Title</Translate>
            </span>
          </dt>
          <dd>{module1Entity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="eduApp.module1.description">Description</Translate>
            </span>
          </dt>
          <dd>{module1Entity.description}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="eduApp.module1.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>{module1Entity.startDate ? <TextFormat value={module1Entity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endData">
              <Translate contentKey="eduApp.module1.endData">End Data</Translate>
            </span>
          </dt>
          <dd>{module1Entity.endData ? <TextFormat value={module1Entity.endData} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="level">
              <Translate contentKey="eduApp.module1.level">Level</Translate>
            </span>
          </dt>
          <dd>{module1Entity.level}</dd>
          <dt>
            <Translate contentKey="eduApp.module1.scenario">Scenario</Translate>
          </dt>
          <dd>{module1Entity.scenario ? module1Entity.scenario.title : ''}</dd>
          <dt>
            <Translate contentKey="eduApp.module1.fragment">Fragment</Translate>
          </dt>
          <dd>
            {module1Entity.fragments
              ? module1Entity.fragments.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {module1Entity.fragments && i === module1Entity.fragments.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/module-1" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/module-1/${module1Entity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default Module1Detail;
