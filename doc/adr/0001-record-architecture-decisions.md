# Record architecture decisions

- Number: 1
- Date: 2023-07-19
- Authors: [Futured Team](https://github.com/futured-bot)
- Status: Accepted

## Context

We need to record the architectural decisions made on this project.

## Decision

We will use Architecture Decision Records, as [described by Michael Nygard](http://thinkrelevance.com/blog/2011/11/15/documenting-architecture-decisions).

We will use our [own template](templates/template.md) inspired by
[Swift Evolution](https://github.com/apple/swift-evolution/blob/main/proposal-templates/0000-swift-template.md).

We will manage ADRs with [adr-tools](https://github.com/npryce/adr-tools).

With every new ADR, we will generate new table of contents in [README.md](README.md) file using `adr generate toc` command.

## Alternatives considered

Make no effort to write better documentation.

## Consequences

- Documentation of the project will be improved.
- There will be more time spent on documentation.
- We need to have on mind that we need to write ADR
  when making decisions.
